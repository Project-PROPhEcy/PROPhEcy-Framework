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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 18.07.2014.
 */
public abstract class Calculation implements ICalculation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private FactorCatalog _factorCatalog = null;
    private EventManager _eventManager = null;

    private TupleResults _tupleResults
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
    public FactorCatalog getFactorCatalog() {
        return _factorCatalog;
    }

    /**
     * Gets the used event manager.
     */
    public EventManager getEventManager() {
        return _eventManager;
    }

    /**
     * Gets the tuple results.
     */
    public TupleResults getTupleResults() {
        return _tupleResults;
    }

    /**
     * Gets the number of events in lineage formula.
     */
    public int getEventCount() {
        return _eventCount;
    }

    /**
     * Gets the number of variables in the lineage formula.
     */
    public int getVariablesCount() {
        return _variablesCount;
    }

    /**
     * Gets the number of used masks to calculate the lineage formula.
     */
    public int getMaskCount() {
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
    public Calculation(FactorCatalog factorCatalog, EventManager eventManager) {

        _factorCatalog = factorCatalog;
        _eventManager = eventManager;
    }

    /**
     * Initializes the probability calculations.
     */
    public void initialize() throws Exception {

        countEvents();
        initEvents();
    }

    /**
     * Counts all events for later masking algorithm,
     * starting from the root nodes.
     */
    private void countEvents()
            throws Exception {

        for(ILNode root: _factorCatalog
                .getNodes().values()){
                countEvents(root, root, new HashSet<>());}
    }

    /**
     * Recursive count the events for later masking algorithm.
     * @param root The lineage root node.
     * @param current The current lineage node.
     * @param rootBlockIds All current known block ids.
     */
    private void countEvents(final ILNode root, final ILNode current, Set<Integer> rootBlockIds)
            throws Exception {

        switch(current.getType()) {
            case And: {

                final LAnd lAnd = (LAnd) current;

                for (ILNode child : lAnd.getChildren())
                    countEvents(root, child, rootBlockIds);

                break;
            }
            case Or: {

                final LOr lOr = (LOr) current;

                for (ILNode child : lOr.getChildren())
                    countEvents(root, child, rootBlockIds);

                break;
            }
            case Not: {

                final LNot lNot = (LNot)current;

                countEvents(root, lNot.getChild(),
                        rootBlockIds);

                break;
            }
            case Source: {

                final LSource lSource = (LSource) current;

                // We first save here all known source block ids,
                // before we push all of them to the global known
                // block ids for this root. So we can ensure, that
                // block ids which occur just in one source don't
                // need to be masked. They can just be calculated
                // as exclusive or.
                HashSet<Integer> sourceBlockIds = new HashSet<>();

                for(Event event: lSource.getEvents()) {

                    _eventCount++;

                    if(rootBlockIds.contains(event.getBID())) {

                        // If the event isn't already marked
                        // we need to mark all events for the
                        // specific block id.

                        if(!event.needMask(root)) {
                            for (Event bidEvent : _eventManager
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

                break;
            }
            case True: {

                // Ignore this case.
                break;
            }
            case False: {

                // Ignore this case.
                break;
            }
            default:

                throw new Exception(String.format(
                        "The lineage node: %s is unknown.",
                        current.getType()));
        }
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
    public void resetEvents(int bid) {

        // The event with maximum probability
        // within the specific block id.
        Event selected = null;

        for(Event event: _eventManager.getAll(bid)) {

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
    public Set<Mask> genMasks(int bid, Mask currentMask) {

        Set<Mask> masks = new LinkedHashSet<>();

        Mask fMask = new Mask(bid, -1, 0.0,
                currentMask.getLevel() + 1,
                currentMask.getLevelProb());

        for(Event event: _eventManager.getAll(bid)) {

            fMask.setProb(fMask.getProb()
                    + event.getProb());

            Mask mask = new Mask(bid, event.getTID(),
                    event.getProb(), currentMask.getLevel() + 1,
                    currentMask.getLevelProb() * event.getProb());

            masks.add(mask);
        }

        fMask.setProb(1.0 - fMask.getProb());
        fMask.setLevelProb(fMask.getLevelProb() * fMask.getProb());

        masks.add(fMask);

        return masks;
    }

    /**
     * Sets the mask with the specific mask level
     * and optimize the logical parent nodes.
     * @param mask The mask.
     */
    public void setMask(Mask mask){
        setMask(mask, null, 0.0);
    }

    /**
     * Sets the mask with the specific mask level
     * and optimize the logical parent nodes.
     * @param mask The mask.
     * @param current The current node.
     * @param childProb The child probability.
     */
    private void setMask(Mask mask, ILNode current, Double childProb) {

        if(current == null) {

            for(Event event: _eventManager
                    .getAll(mask.getBID())) {

                // Set the mask level of the
                // event to the specific level.
                event.setMaskLevel(mask.getLevel());

                // Set the calculated
                // event mask probability.
                event.setCurrentProb(
                        mask.getProb(event.getTID()));

                // Optimize the parents.
                for(ILNode parent: event.getParents())
                    setMask(mask, parent, event.getCurrentProb());
            }
        }
        else {

            Double currentProb = childProb;
            boolean optimize = false;

            switch(current.getType()) {
                case And: {

                    LAnd lAnd = (LAnd) current;

                    if (childProb == 0.0)
                        optimize = true;

                    if (lAnd.childCount() == 1)
                        optimize = true;

                        break;
                }
                case Or: {

                    LOr lOr = (LOr) current;

                    if (childProb == 1.0)
                        optimize = true;

                    if (lOr.childCount() == 1)
                        optimize = true;

                    break;
                }
                case Not: {

                    if (childProb == 1.0) {
                        currentProb = 0.0;
                        optimize = true;
                    }

                    if (childProb == 0.0) {
                        currentProb = 1.0;
                        optimize = true;
                    }

                    break;
                }
                case Source: {

                    LSource source = (LSource) current;

                    if (childProb == 1.0)
                        optimize = true;

                    if (source.getEventCount() == 1)
                        optimize = true;

                    break;
                }
            }

            if(optimize && current.getMaskLevel() == -1
                    && !current.isRoot()) {

                // Set the current values
                // according to the mask.
                current.setCurrentProb(currentProb);

                // Set the mask level.
                current.setMaskLevel(
                        mask.getLevel());

                // Mark the node for de-masking.
                mask.addInvolvedNode(current);

                for(ILNode parent: current.getParents())
                    setMask(mask, parent, currentProb);

            }
        }
    }

    /**
     * Resets all nodes which was involved in
     * the calculations for the specific mask.
     * @param mask The mask.
     */
    public void unsetMask(Mask mask) {

        for(ILNode node: mask.getInvolvedNodes()) {

            node.setCurrentProb(-1.0);
            node.setMaskLevel(-1);
        }
    }
}
