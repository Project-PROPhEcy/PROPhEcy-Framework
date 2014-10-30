package com.prophecy.processing.processor.contexts.lineage.tree.base;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public abstract class LNode implements ILNodeVisitable {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private int _maskLevel = -1;
    private Double _currentProb = -1.0;
    private final Set<LNode> _parents
            = new LinkedHashSet<>();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the mask level of current calculations.
     */
    public final int getMaskLevel() {
        return _maskLevel;
    }

    /**
     * Sets the mask level of current calculations.
     */
    public final void setMaskLevel(final int value) {
        _maskLevel = value;
    }

    /**
     * Gets the current calculated probability.
     */
    public final Double getCurrentProb() {
        return _currentProb;
    }

    /**
     * Sets the current calculation probability.
     */
    public final void setCurrentProb(final Double value) {
        _currentProb = value;
    }

    /**
     * Gets the parent nodes.
     */
    public final Set<LNode> getParents() {
        return Collections.unmodifiableSet(_parents);
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Adds the parent node to the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public final boolean addParent(final LNode parent) {
        return _parents.add(parent);
    }

    /**
     * Removes the parent node from the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public final boolean removeParent(final LNode parent) {
        return _parents.remove(parent);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    public abstract String toTreeString();
}
