/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation.exact;

import com.prophecy.processing.processor.contexts.calculation.Calculation;
import com.prophecy.processing.processor.contexts.calculation.Mask;
import com.prophecy.processing.processor.contexts.calculation.Probability;
import com.prophecy.processing.processor.contexts.lineage.Event;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.construction.FactorCatalog;
import com.prophecy.processing.processor.contexts.lineage.construction.GenTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.*;
import com.prophecy.utility.ListUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alpha_000 on 18.07.2014.
 */
public final class ExactCalculation extends Calculation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private int _nextMaskBID = -1;
    private LSource _nextMaskSource = null;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param factorCatalog The used root factor catalog.
     * @param eventManager  The used event manager.
     */
    public ExactCalculation(final FactorCatalog factorCatalog, final EventManager eventManager) {
        super(factorCatalog, eventManager);
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
    private void computeProbs(final Mask mask)
            throws Exception {

        if( mask.getLevel() > 0 ) {
            _maskCount++;
        }

        for(final Map.Entry<GenTuple, ILNode> entry:
                getFactorCatalog().getNodes().entrySet()) {

            // TODo rausziehen?
            final GenTuple genTuple = entry.getKey();
            final ILNode root = entry.getValue();

            if(root.getMaskLevel() == -1) {

                final Double exactProb = simplAndCalc(
                        root, root, mask);

                if(exactProb != -1) {

                    if(!getTupleResults().contains(genTuple))
                        getTupleResults().add(genTuple);

                    final Probability resultProb = getTupleResults()
                            .getProbability(genTuple);

                    resultProb.addExact(
                            exactProb * mask.getLevelProb());
                }
            }
        }

        if(_nextMaskSource != null) {

            // Create a local copy because we need
            // to reset the global values but need
            // the bid later in this code block in
            // order to reset the events.
            final int nextMaskBIDCopy = _nextMaskBID;

            final Set<Mask> masks = genMasks(nextMaskBIDCopy, mask);

            _nextMaskSource = null;
            _nextMaskBID = -1;

            for(final Mask nextMask: masks) {

                setMask(nextMask);
                computeProbs(nextMask);
                unsetMask(nextMask);
            }

            resetEvents(nextMaskBIDCopy);
        }
    }


    /**
     * Simplifies and calculates the lineage tree.
     * @param root The root node.
     * @param current The current node.
     * @param mask The current mask.
     * @return The calculated probability.
     */
    // TODO: Mit Probability arbeiten
    private Double simplAndCalc(final ILNode root, final ILNode current, final Mask mask)
            throws Exception {

        // If this node was already calculated,
        // just return the current calculated
        // probability.
        if(current.getMaskLevel() != -1)
            return current.getCurrentProb();

        // Set the initial probability.
        Double prob = -1.0;

        switch(current.getType()) {
            case And: {

                final LAnd lAnd = (LAnd)current;

                prob = 1.0;

                for(final ILNode child: lAnd.getChildren()) {

                    final Double cProb = simplAndCalc(
                            root, child, mask);

                    // And can't be fulfilled.
                    if(cProb == 0.0)
                        { prob = 0.0; break; }

                    // Calculation can't be completed.
                    else if(cProb == -1.0)
                        prob = -1.0;

                    // Simple evaluation.
                    else if(prob != -1.0)
                        prob *= cProb;
                }

                break;
            }
            case Or: {

                final LOr lOr = (LOr)current;

                prob = 1.0;

                for(final ILNode child: lOr.getChildren()) {

                    final Double cProb = simplAndCalc(
                            root, child, mask);

                    // Or is already fulfilled.
                    if(cProb == 1.0)
                        { prob = 0.0; break; }

                    // Calculation can't be completed.
                    else if(cProb == -1.0)
                        prob = -1.0;

                    // Simple evaluation.
                    else if(prob != -1.0)
                        prob *= (1.0 - cProb);
                }

                // Simple evaluation.
                if(prob != -1.0)
                    prob = 1.0 - prob;

                break;
            }
            case Not: {

                final LNot lNot = (LNot)current;

                prob = simplAndCalc(
                        root, lNot.getChild(), mask);

                // Simple evaluation.
                if(prob != -1.0)
                    prob = 1.0 - prob;

                break;
            }
            case Source: {

                final LSource lSource = (LSource)current;

                prob = 1.0;

                for(final Map.Entry<Integer, List<Event>> entry: ListUtils.GroupBy(
                        lSource.getEvents(), Event::getBID).entrySet()) {

                    final int bid = entry.getKey();
                    final List<Event> events = entry.getValue();

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

                        prob = -1.0;
                    }
                    else {

                        final Double bidProb = events.stream()
                                .mapToDouble(Event::getCurrentProb).sum();

                        if(bidProb == 1.0)
                        {prob = 0.0; break; }

                        // Add the block id probability
                        // to the source probability.
                        else if(prob != -1.0)
                            prob *= (1.0 - bidProb);
                    }
                }

                if(prob != -1.0)
                    prob = 1.0 - prob;

                break;
            }
            case True: {

                return current.getCurrentProb();
            }
            case False: {

                return current.getCurrentProb();
            }
            default:

                throw new Exception(String.format(
                        "The lineage node: %s is unknown.",
                        current.getType()));
        }

        if(prob != -1.0) {

            current.setCurrentProb(prob);
            current.setMaskLevel(mask.getLevel());

            // Remember the node for
            // the unset mask procedure.
            mask.addInvolvedNode(current);
        }

        return prob;
    }
}
