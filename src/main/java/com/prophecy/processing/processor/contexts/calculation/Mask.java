/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation;

import com.prophecy.processing.processor.contexts.lineage.tree.ILNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 25.07.2014.
 */
public class Mask implements Comparable<Mask> {

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
    private int _bid = -1;


    /**
     * Saves the tuple id.
     */
    private int _tid = -1;


    /**
     * Saves the probability.
     */
    private Double _prob = -1.0;


    /**
     * Saves the mask level.
     */
    private int _level = -1;


    /**
     * Saves the mask level probability.
     */
    private Double _levelProb = -1.0;


    /**
     * Saves the involved nodes for this mask.
     */
    private Set<ILNode> _involvedNodes
            = new HashSet<>();


    /**
     * Saves the remembered block ids for this mask.
     * This is required for the approximation algorithm.
     */
    private Set<Integer> _rememberedBIDs
            = new HashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the block id.
     */
    public int getBID() {
        return _bid;
    }


    /**
     * Gets the tuple id.
     */
    public int getTID() {
        return _tid;
    }


    /**
     * Gets the probability.
     */
    public Double getProb() {
        return _prob;
    }


    /**
     * Sets the probability.
     */
    public void setProb(Double value) {
        _prob = value;
    }


    /**
     * Gets the mask level.
     */
    public int getLevel() {
        return _level;
    }


    /**
     * Gets the mask level probability.
     */
    public Double getLevelProb() {
        return _levelProb;
    }


    /**
     * Sets the level probability.
     */
    public void setLevelProb(Double value) {
        _levelProb = value;
    }


    /**
     * Gets the involved nodes for this mask.
     */
    public Set<ILNode> getInvolvedNodes() {
        return Collections.unmodifiableSet(_involvedNodes);
    }


    /**
     * Gets the remembered block ids for this mask.
     */
    public Set<Integer> getRememberedBID() {
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
    public Mask(int bid, int tid, Double prob, int level, Double levelProb) {

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
    public Double getProb(int tid) {

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
    public boolean addInvolvedNode(ILNode node) {
        return _involvedNodes.add(node);
    }


    /**
     * Removes the involved node from the mask.
     * @param node The involved node.
     * @return The boolean value.
     */
    public boolean removeInvolvedNode(ILNode node) {
        return _involvedNodes.remove(node);
    }


    /**
     * Determines whether the bloc kid is already remembered.
     * @param bid The block id.
     * @return The boolean value.
     */
    public boolean containsRememberedBID(int bid) {
        return _rememberedBIDs.contains(bid);
    }


    /**
     * Adds a new remembered block id to the mask.
     * @param bid The block id.
     * @return The boolean value.
     */
    public boolean addRememberedBID(int bid) {
        return _rememberedBIDs.add(bid);
    }


    /**
     * Removes the remembered block id from the mask.
     * @param bid The block id.
     * @return The boolean value.
     */
    public boolean removeRememberedBID(int bid) {
        return _rememberedBIDs.remove(bid);
    }


    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     */
    @Override
    @SuppressWarnings("NullableProblems")
    public int compareTo(Mask o) {

        if(_prob - o._prob < 0.0)
            return 1;

        return -1;
    }

    /**
     * Clear the involved nodes and the involved block ids.
     */
    public void clear() {

        _involvedNodes.clear();
        _rememberedBIDs.clear();
    }
}