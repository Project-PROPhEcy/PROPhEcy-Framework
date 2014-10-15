/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage;

import com.prophecy.processing.processor.contexts.lineage.tree.ILNode;
import com.prophecy.processing.processor.contexts.lineage.tree.LSource;

import java.util.*;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public final class Event implements Comparable<Event> {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Generates an unique hashcode for the specific input.
     * @param bid The block id.
     * @param tid The tuple id.
     * @param prob The initial probability.
     * @return The hash code.
     */
    public static int HashCode(final int bid, final int tid, final Double prob) {

        int result;
        long temp;

        result = bid;
        result = 31 * result + tid;
        temp = Double.doubleToLongBits(prob);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }


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
     * Saves the initial probability.
     */
    private final double _prob;


    /**
     * Saves the current probability.
     */
    private double _currentProb = -1.0;


    /**
     * Saves the current upper bound.
     */
    private double _currentUB = -1.0;


    /**
     * Saves the current lower bound.
     */
    private double _currentLB = -1.0;


    /**
     * Saves whether this event has the
     * maximum probability within the block id.
     */
    private boolean _hasMaxProb = false;


    /**
     * Saves the current mask level.
     */
    private int _maskLevel = 0;


    /**
     * Saves the hash code.
     */
    private final int _hashCode;


    /**
     * Saves the need mask state.
     */
    private final Set<ILNode> _needMask
            = new HashSet<>();


    /**
     * Saves the responsible source parents.
     */
    private final Set<LSource> _parents
            = new LinkedHashSet<>();


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
     * Gets the initial probability.
     */
    public final double getProb() {
        return _prob;
    }


    /**
     * Gets the responsible source parents.
     */
    public final Set<LSource> getParents() {
        return _parents;
    }


    /**
     * Determines whether this event has the
     * maximum probability within the block id.
     */
    public final boolean hasMaxProb() {
        return _hasMaxProb;
    }


    /**
     * Sets the value whether this event has
     * the maximum probability within the block id.
     */
    public final void hasMaxProb(final boolean value) {
        _hasMaxProb = value;
    }


    /**
     * Gets the current mask level.
     */
    public final int getMaskLevel() {
        return _maskLevel;
    }


    /**
     * Sets the current mask level.
     */
    public final void setMaskLevel(final int value) {
        _maskLevel = value;
    }


    /**
     * Gets the current probability.
     */
    public final double getCurrentProb() {
        return _currentProb;
    }


    /**
     * Sets the current probability.
     */
    public final void setCurrentProb(final double value) {
        _currentProb = value;
    }


    /**
     * Gets the current upper bound.
     */
    public final double getCurrentUB() {
        return _currentUB;
    }


    /**
     * Sets the current upper bound.
     */
    public final void setCurrentUB(final double value) {
        _currentUB = value;
    }


    /**
     * Gets the current lower bound.
     */
    public final double getCurrentLB() {
        return _currentLB;
    }


    /**
     * Sets the current lower bound.
     */
    public final void setCurrentLB(final double value) {
        _currentLB = value;
    }


    /**
     * Gets the hash code for the event.
     */
    @Override
    public final int hashCode() {
        return _hashCode;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param bid The block id.
     * @param tid The tuple id.
     * @param prob The initial probability.
     */
    public Event(final int bid, final int tid, final Double prob) {

        _bid = bid;
        _tid = tid;
        _prob = prob;

        _currentProb = prob;
        _currentUB = prob;
        _currentLB = prob;

        _hashCode = HashCode(
                bid, tid, prob);
    }


    /**
     * Adds the parent source to this event.
     * @param source The parent source.
     * @return The boolean value.
     */
    public final boolean addParent(final LSource source) {
        return _parents.add(source);
    }


    /**
     * Removes the parent source from this event.
     * @param source The parent source.
     * @return The boolean value.
     */
    public final boolean removeParent(final LSource source) {
        return _parents.remove(source);
    }


    /**
     * Adds a root node for which the event needs a mask.
     * @param root The root node.
     */
    public final void addNeedMask(final ILNode root) {
        _needMask.add(root);
    }


    /**
     * Removes a root node for which the event needs a mask.
     * @param root The root node.
     */
    public final void removeNeedMask(final ILNode root) {
        _needMask.remove(root);
    }


    /**
     * Determines whether the event needs to be masked
     * for the specific root node.
     * This value don't change after masking, so just
     * check the mask level which is greater than zero
     * if the block id is masked.
     */
    public final boolean needMask(final ILNode root) {
        return _needMask.contains(root);
    }


    /**
     * Used to order the events by their block id.
     * @param o The other event.
     * @return The compare result.
     */
    @Override
    public final int compareTo(final Event o) {

        // Check if this event
        // is after the other event.
        if(_bid < o._bid) return 1;
        else return -1;
    }


    /**
     * Determines whether the two events are equal.
     * @param o The other event.
     * @return The boolean value.
     */
    @Override
    public final boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final Event event = (Event) o;

        return _bid == event._bid
                && Double.compare(event._prob, _prob) == 0
                && _tid == event._tid;
    }
}
