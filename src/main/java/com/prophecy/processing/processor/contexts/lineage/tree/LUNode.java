package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.utility.node.UNode;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public class LUNode extends UNode<LType, ILNode> implements ILNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the mask level of current calculations.
     */
    private int _maskLevel = -1;


    /**
     * Saves the current calculated probability.
     */
    private Double _currentProb = -1.0;


    /**
     * Saves the node parents.
     */
    private final Set<ILNode> _parents
            = new LinkedHashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the mask level of current calculations.
     */
    @Override
    public final int getMaskLevel() {
        return _maskLevel;
    }

    /**
     * Sets the mask level of current calculations.
     */
    @Override
    public final void setMaskLevel(final int value) {
        _maskLevel = value;
    }

    /**
     * Gets the current calculated probability.
     */
    @Override
    public final Double getCurrentProb() {
        return _currentProb;
    }

    /**
     * Sets the current calculation probability.
     */
    @Override
    public final void setCurrentProb(final Double value) {
        _currentProb = value;
    }

    /**
     * Sets the child node.
     */
    @Override
    public final void setChild(final ILNode value) {

        // Remove this node
        // from child parents.
        if(getChild() != null)
            getChild().removeParent(this);

        // Set the new child node.
        super.setChild(value);

        // Add this node to
        // the child parents.
        value.addParent(this);
    }

    /**
     * Gets the parent nodes.
     */
    @Override
    public final Set<ILNode> getParents() {
        return Collections.unmodifiableSet(_parents);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param type The node type.
     * @param defaultChild The default child node.
     */
    public LUNode(final LType type, final ILNode defaultChild) {
        super(type);

        // We need to set the
        // default child node.
        this.setChild(defaultChild);
    }

    /**
     * Adds the parent node to the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public final boolean addParent(final ILNode parent) {
        return _parents.add(parent);
    }

    /**
     * Removes the parent node from the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public final boolean removeParent(final ILNode parent) {
        return _parents.remove(parent);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return String.format( "%s { %s }",
                toString(), getChild().toTreeString() );
    }
}
