/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation.approx;

import com.prophecy.processing.processor.contexts.calculation.Calculation;
import com.prophecy.processing.processor.contexts.calculation.Mask;
import com.prophecy.processing.processor.contexts.calculation.Probability;
import com.prophecy.processing.processor.contexts.lineage.Event;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.construction.FactorCatalog;
import com.prophecy.processing.processor.contexts.lineage.construction.GenTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by alpha_000 on 24.07.2014.
 */
public class ApproxCalculation extends Calculation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private int _nextMaskBID = -1;
    private LSource _nextMaskSource = null;

    private final boolean _isRelativeError;
    private final Double _absoluteError;
    private final Double _relativeError;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param factorCatalog The used root factor catalog.
     * @param eventManager The used event manager.
     * @param error The error constant.
     * @param isRelativeError Is relative error.
     */
    public ApproxCalculation(final FactorCatalog factorCatalog, final EventManager eventManager, final Double error, final boolean isRelativeError) {
        super(factorCatalog, eventManager);

        _isRelativeError = isRelativeError;

        _absoluteError = 2 * error;
        _relativeError = (1 - error) / (1 + error);
    }

    /**
     * Executes the probability calculations.
     */
    @Override
    public final void execute()
            throws Exception {
        computeProbs(Mask.INIT);
    }

    /**
     * Computes the probabilities for a specific mask level.
     * @param mask The current used mask.
     */
    private final void computeProbs(Mask mask)
            throws Exception {

        boolean masking = false;
        if( mask.getLevel() > 0 ) {
            _maskCount++;
        }

        for(final Map.Entry<GenTuple, ILNode> entry:
                getFactorCatalog().getNodes().entrySet()) {

            final GenTuple genTuple = entry.getKey();
            final ILNode root = entry.getValue();

            if(root.getMaskLevel() == -1) {

                final Probability prob = simplAndApprox(
                        root, root, mask);

                if(prob.getUB() - prob.getLB() == 0
                        || !_isRelativeError && prob.getUB() - prob.getLB() <= _absoluteError
                        || _isRelativeError && _relativeError <= prob.getLB() / prob.getUB()) {

                    if(!getTupleResults().contains(genTuple))
                        getTupleResults().add(genTuple);

                    final Probability resultProb = getTupleResults()
                            .getProbability(genTuple);

                    resultProb.addLB(prob.getLB() * mask.getLevelProb());
                    resultProb.addUB(prob.getUB() * mask.getLevelProb());

                    // We set the mask level in order to
                    // prevent further calculations.
                    root.setMaskLevel(mask.getLevel());

                    // We remember the root node for
                    // this mask level to reset it later.
                    mask.addInvolvedNode(root);
                }
                else {
                    masking = true;
                }
            }
        }

        if(masking) {

            // Create a local copy because we need
            // to reset the global values but need
            // the bid later in this code block in
            // order to reset the events.
            final int nextMaskBIDCopy = _nextMaskBID;

            final Mask[] masks = genMasks(nextMaskBIDCopy, mask);

            _nextMaskSource = null;
            _nextMaskBID = -1;

            for(Mask nextMask: masks) {

                setMask(nextMask);
                computeProbs(nextMask);
                unsetMask(nextMask);
            }

            resetEvents(nextMaskBIDCopy);
        }
    }


    /**
     * Simplifies and approximates the lineage tree.
     * @param root The root node.
     * @param current The current node.
     * @param mask The current mask.
     * @return The calculated probability.
     */
    private Probability simplAndApprox(ILNode root, ILNode current, Mask mask)
            throws Exception {

        // If this node was already calculated,
        // just return the current calculated
        // probability.
        if(current.getMaskLevel() != -1)
            return new Probability(
                    current.getCurrentProb(),
                    current.getCurrentProb(),
                    current.getCurrentProb()
            );

        // Set the initial probability.
        Probability prob = new Probability(-1.0);

        switch(current.getType()) {
            case And: {

                final LAnd lAnd = (LAnd)current;

                prob.setAll(1.0);

                for(final ILNode child: lAnd.getChildren()) {

                    final Probability cProb = simplAndApprox(
                            root, child, mask);

                    // And can't be fulfilled.
                    if(cProb.getExact() == 0.0)
                    { prob.setAll(0.0); break; }

                    // Calculation can't be completed.
                    else if(cProb.getExact() == -1.0)
                        prob.setExact(-1.0);

                    // Simple evaluation.
                    else if(prob.getExact() != -1.0)
                        prob.mulExact(cProb.getExact());

                    // Calculate the bound probabilities.
                    prob.mulLB(cProb.getLB());
                    prob.mulUB(cProb.getUB());
                }

                break;
            }
            case Or: {

                final LOr lOr = (LOr)current;

                prob.setAll(1.0);

                for(final ILNode child: lOr.getChildren()) {

                    Probability cProb = simplAndApprox(
                            root, child, mask);

                    // Or is already fulfilled.
                    if(cProb.getExact() == 1.0)
                    { prob.setAll(0.0); break; }

                    // Calculation can't be completed.
                    else if(cProb.getExact() == -1.0)
                        prob.setExact(-1.0);

                    // Simple evaluation.
                    else if(prob.getExact() != -1.0)
                        prob.mulExact(1.0 - cProb.getExact());

                    // Calculate the bound probabilities.
                    prob.mulLB(1.0 - cProb.getLB());
                    prob.mulUB(1.0 - cProb.getUB());
                }

                // Simple evaluation.
                if(prob.getExact() != -1.0)
                    prob.setExact(1.0 - prob.getExact());

                // Calculate the bound probabilities.
                prob.setLB(1.0 - prob.getLB());
                prob.setUB(1.0 - prob.getUB());

                break;
            }
            case Not: {

                final LUNot lNot = (LUNot)current;

                prob = simplAndApprox(
                        root, lNot.getChild(), mask);

                // Simple evaluation.
                if(prob.getExact() != -1.0)
                    prob.setExact(1.0 - prob.getExact());

                prob.setLB(1.0 - prob.getLB());
                prob.setUB(1.0 - prob.getUB());

                break;
            }
            case Source: {

                final LSource lSource = (LSource)current;

                prob.setAll(1.0);

                Map<Integer, List<Event>> bidGroups = lSource.getEvents().stream()
                        .collect(Collectors.groupingBy(Event::getBID));

                int bid;
                List<Event> events;

                for(final Map.Entry<Integer, List<Event>> entry: bidGroups.entrySet()) {

                    bid = entry.getKey();
                    events = entry.getValue();

                    // Check if the mask level is 0.
                    // If it's true the event isn't
                    // masked yet. Then we check if
                    // the event needs a mask.

                    if(events.get(0).getMaskLevel() == 0
                            && events.get(0).needMask(root)) {

                        // Check if the source has an
                        // higher priority as the previous.
                        if(_nextMaskBID == -1 || _nextMaskSource
                                .getMaskPriority() < lSource.getMaskPriority()) {

                            _nextMaskBID = bid;
                            _nextMaskSource = lSource;
                        }

                        prob.setExact(-1.0);

                        for(final Event event: events) {
                            if(event.hasMaxProb()
                                    && !(mask.containsRememberedBID(event.getBID()))) {

                                // Here we have an unmasked event
                                // which has the highest probability
                                // in the block id. Besides it's the
                                // first encounter so we can use the
                                // mask probability as bounds.

                                prob.mulLB(1.0 - event.getCurrentProb());
                                prob.mulUB(1.0 - event.getCurrentProb());

                                mask.addRememberedBID(event.getBID());
                            }
                            else {

                                // Here is nothing special, we
                                // just use the full bound range.

                                //lb *= (1.0 - 0.0)
                                //ub *= (1.0 - 1.0)

                                prob.setUB(0.0);
                            }
                        }
                    }
                    else {

                        final Double bidProb = events.stream()
                                .mapToDouble(Event::getCurrentProb).sum();

                        if(bidProb == 1.0)
                        { prob.setAll(0.0); break; }

                        // Add the block id probability
                        // to the source probability.
                        else if(prob.getExact() != -1.0)
                            prob.mulExact(1.0 - bidProb);

                        // Calculate the bounds.
                        prob.mulLB(1.0 - bidProb);
                        prob.mulUB(1.0 - bidProb);
                    }
                }

                // Simple evaluation.
                if(prob.getExact() != -1.0)
                    prob.setExact(1.0 - prob.getExact());

                // Calculate the final bound probabilities.
                prob.setLB(1.0 - prob.getLB());
                prob.setUB(1.0 - prob.getUB());

                break;
            }
            case True: {

                prob.setAll(current.getCurrentProb());
                return prob;
            }
            case False: {

                prob.setAll(current.getCurrentProb());
                return prob;
            }
            default:

                throw new Exception(String.format(
                        "The lineage node: %s is unknown.",
                        current.getType()));
        }

        if(prob.getExact() != -1.0) {

            current.setCurrentProb(prob.getExact());
            current.setMaskLevel(mask.getLevel());

            // Remember the node for
            // the unset mask procedure.
            mask.addInvolvedNode(current);
        }

        return prob;
    }
}
