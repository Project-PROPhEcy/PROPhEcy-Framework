/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation;

import com.prophecy.processing.processor.contexts.lineage.tree.ILNode;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 25.07.2014.
 */
public final class Mask implements Comparable<Mask> {

    //----------------------------------------
    // Static Constants
    //----------------------------------------


    /**
     * Gets the initial mask for calculations.
     */
    public static final Mask INIT
            = new Mask(-1, -1, 1.0, 0, 1.0);


    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the block id.
     */
    private final int _bid;


    /**
     * Saves the tuple id.
     */
    private final int _tid;


    /**
     * Saves the probability.
     */
    private final Double _prob;


    /**
     * Saves the mask level.
     */
    private final int _level;


    /**
     * Saves the mask level probability.
     */
    private final Double _levelProb;


    /**
     * Saves the involved nodes for this mask.
     */
    private final Set<ILNode> _involvedNodes
            = new HashSet<>();


    /**
     * Saves the remembered block ids for this mask.
     * This is required for the approximation algorithm.
     */
    private final Set<Integer> _rememberedBIDs
            = new HashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the block id.
     */
    public final int getBID() {
        return _bid;
    }


    /**
     * Gets the tuple id.
     */
    public final int getTID() {
        return _tid;
    }


    /**
     * Gets the probability.
     */
    public final Double getProb() {
        return _prob;
    }


    /**
     * Gets the mask level.
     */
    public final int getLevel() {
        return _level;
    }


    /**
     * Gets the mask level probability.
     */
    public final Double getLevelProb() {
        return _levelProb;
    }


    /**
     * Gets the involved nodes for this mask.
     */
    public final Set<LNode> getInvolvedNodes() {
        return Collections.unmodifiableSet(_involvedNodes);
    }


    /**
     * Gets the remembered block ids for this mask.
     */
    // TODO wofür war das nochmal?
    public final Set<Integer> getRememberedBID() {
        return Collections.unmodifiableSet(_rememberedBIDs);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param bid The block id.
     * @param tid The tuple id.
     * @param prob The probability.
     * @param level The mask level.
     * @param levelProb The mask level probability.
     */
    public Mask(final int bid, final int tid, final Double prob, final int level, final Double levelProb) {

        _bid = bid;
        _tid = tid;
        _prob = prob;
        _level = level;
        _levelProb = levelProb;
    }


    /**
     * Gets the probability of the specific tuple id.
     * @param tid The tuple id.
     * @return The probability.
     */
    public final Double getProb(final int tid) {

        // If the tuple id is the
        // selected tuple id for
        // this mask return one.
        if(_tid == tid)
            return 1.0;

        return 0.0;
    }


    /**
     * Adds a new involved node to the mask.
     * @param node The involved node.
     * @return The boolean value.
     */
    public final boolean addInvolvedNode(final LNode node) {
        return _involvedNodes.add(node);
    }


    /**
     * Removes the involved node from the mask.
     * @param node The involved node.
     * @return The boolean value.
     */
    public final boolean removeInvolvedNode(final ILNode node) {
        return _involvedNodes.remove(node);
    }


    /**
     * Determines whether the bloc kid is already remembered.
     * @param bid The block id.
     * @return The boolean value.
     */
    public final boolean containsRememberedBID(final int bid) {
        return _rememberedBIDs.contains(bid);
    }


    /**
     * Adds a new remembered block id to the mask.
     * @param bid The block id.
     * @return The boolean value.
     */
    public final boolean addRememberedBID(final int bid) {
        return _rememberedBIDs.add(bid);
    }


    /**
     * Removes the remembered block id from the mask.
     * @param bid The block id.
     * @return The boolean value.
     */
    public final boolean removeRememberedBID(final int bid) {
        return _rememberedBIDs.remove(bid);
    }


    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public final int compareTo(Mask o) {

        if(_prob - o._prob < 0.0)
            return 1;

        return -1;
    }

    /**
     * Clear the involved nodes and the involved block ids.
     */
    public final void clear() {

        _involvedNodes.clear();
        _rememberedBIDs.clear();
    }
}