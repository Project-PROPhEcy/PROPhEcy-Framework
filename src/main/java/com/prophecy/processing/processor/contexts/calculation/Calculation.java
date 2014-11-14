/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation;

import com.prophecy.processing.output.TupleResults;
import com.prophecy.processing.processor.contexts.lineage.Event;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.construction.FactorCatalog;
import com.prophecy.processing.processor.contexts.lineage.tree.*;
import com.prophecy.processing.processor.contexts.lineage.tree.base.ILNodeVisitor;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;
import com.prophecy.utility.Reference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Calculation implements ICalculation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final FactorCatalog _factorCatalog;
    private final EventManager _eventManager;

    private final TupleResults _tupleResults
            = new TupleResults();

    private int _eventCount = 0;
    private int _variablesCount = 0;
    protected int _maskCount = 0;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the used root factor catalog.
     */
    public final FactorCatalog getFactorCatalog() {
        return _factorCatalog;
    }

    /**
     * Gets the used event manager.
     */
    public final EventManager getEventManager() {
        return _eventManager;
    }

    /**
     * Gets the tuple results.
     */
    public final TupleResults getTupleResults() {
        return _tupleResults;
    }

    /**
     * Gets the number of events in lineage formula.
     */
    public final int getEventCount() {
        return _eventCount;
    }

    /**
     * Gets the number of variables in the lineage formula.
     */
    public final int getVariablesCount() {
        return _variablesCount;
    }

    /**
     * Gets the number of used masks to calculate the lineage formula.
     */
    public final int getMaskCount() {
        return _maskCount;
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param factorCatalog The used root factor catalog.
     * @param eventManager The used event manager.
     */
    public Calculation(final FactorCatalog factorCatalog, final EventManager eventManager) {
        _factorCatalog = factorCatalog;
        _eventManager = eventManager;
    }

    /**
     * Initializes the probability calculations.
     */
    public final void initialize() {
        countEvents();
        initEvents();
    }

    /**
     * Counts all events for later masking algorithm,
     * starting from the root nodes.
     */
    private void countEvents() {
        _factorCatalog.getNodes()
                .values().forEach(this::countEvents);
    }

    /**
     * Recursive count the events for later masking algorithm.
     * @param root The lineage root node.
     */
    private void countEvents(final LNode root) {

        final Set<Integer> rootBlockIds = new HashSet<>();
        final ILNodeVisitor visitor = new ILNodeVisitor<Void>() {

            @Override
            public void visit(final LBAnd lbAnd, final Void param) {
                lbAnd.getLeftChild().accept(this, null);
                lbAnd.getRightChild().accept(this, null);
            }

            @Override
            public void visit(final LBOr lbOr, final Void param) {
                lbOr.getLeftChild().accept(this, null);
                lbOr.getRightChild().accept(this, null);
            }

            @Override
            public void visit(final LFalse lFalse, final Void param) {
            }

            @Override
            public void visit(final LTrue lTrue, final Void param) {
            }

            @Override
            public void visit(final LNAnd lnAnd, final Void param) {
                for(final LNode child: lnAnd.children()) {
                    child.accept(this, null);
                }
            }

            @Override
            public void visit(final LNOr lnOr, final Void param) {
                for(final LNode child: lnOr.children()) {
                    child.accept(this, null);
                }
            }

            @Override
            public void visit(final LUNot luNot, final Void param) {
                luNot.getChild().accept(this, null);
            }

            @Override
            public void visit(final LSource lSource, final Void param) {

                // We first save here all known source block ids,
                // before we push all of them to the global known
                // block ids for this root. So we can ensure, that
                // block ids which occur just in one source don't
                // need to be masked. They can just be calculated
                // as exclusive or.
                final HashSet<Integer> sourceBlockIds = new HashSet<>();

                for(final Event event: lSource.getEvents()) {

                    _eventCount++;

                    if(rootBlockIds.contains(event.getBID())) {

                        // If the event isn't already marked
                        // we need to mark all events for the
                        // specific block id.

                        if(!event.needMask(root)) {
                            for (final Event bidEvent : _eventManager
                                    .getAll(event.getBID())) {
                                bidEvent.addNeedMask(root);
                                _variablesCount++;
                            }
                        }
                    }
                    else {

                        // Add the bid to the known source block ids in
                        // order to add them later to the root block ids.
                        // We do this later in order to not mask block ids
                        // which are only in this source present.
                        sourceBlockIds.add(event.getBID());
                    }
                }

                // Add all source block ids to the root block ids.
                // So we can recognize block ids which occur in
                // different sources from the same root.
                rootBlockIds.addAll(sourceBlockIds);
            }
        };

        root.accept(visitor, null);
    }

    /**
     * Initializes the probabilities for the events.
     * It's important, that this function is called after
     * the count events function.
     */
    private void initEvents() {
        _eventManager.getBIDs()
                .forEach(this::resetEvents);
    }

    /**
     * Resets the events for the specific block id.
     * @param bid The block id.
     */
    public final void resetEvents(final int bid) {

        // The event with maximum probability
        // within the specific block id.
        Event selected = null;

        for(final Event event: _eventManager.getAll(bid)) {

            event.setMaskLevel(0);
            event.setCurrentProb(
                    event.getProb());

            if(selected == null
                    || selected.getProb() < event.getProb())
                selected = event;
        }

        // Set indicator for maximum
        // probability for the selected event.
        assert selected != null;
        selected.hasMaxProb(true);
    }

    /**
     * Generates the masks for the specific block id.
     * @param bid The block id.
     * @param currentMask The current mask.
     * @return The resulting mask set.
     */
    public final Mask[] genMasks(final int bid, final Mask currentMask) {

        final List<Event> events = _eventManager.getAll(bid);
        final int eventsSize = events.size();
        final Mask[] masks = new Mask[eventsSize + 1];

        Event event;
        double fMaskProb = 0.0;
        for(int i = 0; i < eventsSize; i++) {
            event = events.get(i);
            fMaskProb += event.getProb();
            masks[i] = new Mask(bid, event.getTID(),
                    event.getProb(), currentMask.getLevel() + 1,
                    currentMask.getLevelProb() * event.getProb());
        }

        // False mask
        fMaskProb = 1.0 - fMaskProb;
        masks[eventsSize] = new Mask(bid, -1, fMaskProb,
                currentMask.getLevel() + 1,
                currentMask.getLevelProb() * fMaskProb);

        return masks;
    }

    /**
     * Sets the mask with the specific mask level
     * and optimize the logical parent nodes.
     * @param mask The mask.
     */
    public final void setMask(final Mask mask){
        setMask(mask, null, 0.0);
    }

    /**
     * Sets the mask with the specific mask level
     * and optimize the logical parent nodes.
     * @param mask The mask.
     * @param current The current node.
     * @param childProb The child probability.
     */
    private void setMask(final Mask mask, final LNode current, final Double childProb) {

        if(current == null) {

            for(final Event event: _eventManager
                    .getAll(mask.getBID())) {

                // Set the mask level of the
                // event to the specific level.
                event.setMaskLevel(mask.getLevel());

                // Set the calculated
                // event mask probability.
                event.setCurrentProb(
                        mask.getProb(event.getTID()));

                // Optimize the parents.
                for(final LNode parent: event.getParents())
                    setMask(mask, parent, event.getCurrentProb());
            }
        }
        else {

            final Reference<Double> currentProb = new Reference<>(childProb);
            final Reference<Boolean> optimize = new Reference<>(false);
            ILNodeVisitor visitor = new ILNodeVisitor<Void>() {

                @Override
                public void visit(LBAnd lbAnd, Void param) {
                    if (childProb == 0.0) {
                        optimize.value = true;
                    }
                }

                @Override
                public void visit(LBOr lbOr, Void param) {
                    if (childProb == 1.0) {
                        optimize.value = true;
                    }
                }

                @Override
                public void visit(LFalse lFalse, Void param) {
                }

                @Override
                public void visit(LTrue lTrue, Void param) {
                }

                @Override
                public void visit(LNAnd lnAnd, Void param) {
                    if (childProb == 0.0) {
                        optimize.value = true;
                    }
                    if (lnAnd.size() == 1) {
                        optimize.value = true;
                    }
                }

                @Override
                public void visit(LNOr lnOr, Void param) {
                    if (childProb == 1.0) {
                        optimize.value = true;
                    }
                    if (lnOr.size() == 1) {
                        optimize.value = true;
                    }
                }

                @Override
                public void visit(LUNot luNot, Void param) {
                    if (childProb == 1.0) {
                        currentProb.value = 0.0;
                        optimize.value = true;
                    }
                    if (childProb == 0.0) {
                        currentProb.value = 1.0;
                        optimize.value = true;
                    }
                }

                @Override
                public void visit(LSource lSource, Void param) {
                    if (childProb == 1.0) {
                        optimize.value = true;
                    }
                    if (lSource.getEventCount() == 1) {
                        optimize.value = true;
                    }
                }
            };

            // Start checking for optimizations.
            current.accept(visitor, null);

            if(optimize.value && current.getMaskLevel() == -1
                    && !current.isRoot()) {

                // Set the current values
                // according to the mask.
                current.setCurrentProb(currentProb.value);

                // Set the mask level.
                current.setMaskLevel(
                        mask.getLevel());

                // Mark the node for de-masking.
                mask.addInvolvedNode(current);

                for(final LNode parent: current.getParents())
                    setMask(mask, parent, currentProb.value);
            }
        }
    }

    /**
     * Resets all nodes which was involved in
     * the calculations for the specific mask.
     * @param mask The mask.
     */
    public final void unsetMask(final Mask mask) {
        for(final LNode node: mask.getInvolvedNodes()) {
            node.setCurrentProb(-1.0);
            node.setMaskLevel(-1);
        }
    }
}
