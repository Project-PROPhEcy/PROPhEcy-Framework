/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation.exact;

import com.prophecy.processing.output.TupleResults;
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
public class ExactCalculation extends Calculation {

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
    public ExactCalculation(FactorCatalog factorCatalog, EventManager eventManager) {
        super(factorCatalog, eventManager);
    }


    /**
     * Executes the probability calculations.
     */
    @Override
    public void execute()
            throws Exception {
        computeProbs(Mask.INIT);
    }


    /**
     * Computes the probabilities for a specific mask level.
     * @param mask The current used mask.
     */
    private void computeProbs(Mask mask)
            throws Exception {

        if( mask.getLevel() > 0 ) {
            _maskCount++;
        }

        for(Map.Entry<GenTuple, ILNode> entry:
                getFactorCatalog().getNodes().entrySet()) {

            GenTuple genTuple = entry.getKey();
            ILNode root = entry.getValue();

            if(root.getMaskLevel() == -1) {

                Double exactProb = simplAndCalc(
                        root, root, mask);

                if(exactProb != -1) {

                    if(!getTupleResults().contains(genTuple))
                        getTupleResults().add(genTuple);

                    Probability resultProb = getTupleResults()
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
            int nextMaskBIDCopy = _nextMaskBID;

            Set<Mask> masks = genMasks(nextMaskBIDCopy, mask);

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
     * Simplifies and calculates the lineage tree.
     * @param root The root node.
     * @param current The current node.
     * @param mask The current mask.
     * @return The calculated probability.
     */
    // TODO: Mit Probability arbeiten
    private Double simplAndCalc(ILNode root, ILNode current, Mask mask)
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

                LAnd lAnd = (LAnd)current;

                prob = 1.0;

                for(ILNode child: lAnd.getChildren()) {

                    Double cProb = simplAndCalc(
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

                LOr lOr = (LOr)current;

                prob = 1.0;

                for(ILNode child: lOr.getChildren()) {

                    Double cProb = simplAndCalc(
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

                LNot lNot = (LNot)current;

                prob = simplAndCalc(
                        root, lNot.getChild(), mask);

                // Simple evaluation.
                if(prob != -1.0)
                    prob = 1.0 - prob;

                break;
            }
            case Source: {

                LSource lSource = (LSource)current;

                prob = 1.0;

                for(Map.Entry<Integer, List<Event>> entry: ListUtils.GroupBy(
                        lSource.getEvents(), Event::getBID).entrySet()) {

                    int bid = entry.getKey();
                    List<Event> events = entry.getValue();

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

                        Double bidProb = events.stream()
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
