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
    private Set<ILNode> _parents
            = new LinkedHashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the mask level of current calculations.
     */
    @Override
    public int getMaskLevel() {
        return _maskLevel;
    }

    /**
     * Sets the mask level of current calculations.
     */
    @Override
    public void setMaskLevel(int value) {
        _maskLevel = value;
    }

    /**
     * Gets the current calculated probability.
     */
    @Override
    public Double getCurrentProb() {
        return _currentProb;
    }

    /**
     * Sets the current calculation probability.
     */
    @Override
    public void setCurrentProb(Double value) {
        _currentProb = value;
    }

    /**
     * Sets the child node.
     */
    @Override
    public void setChild(ILNode value) {

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
    public Set<ILNode> getParents() {
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
    public LUNode(LType type, ILNode defaultChild) {
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
    public boolean addParent(ILNode parent) {
        return _parents.add(parent);
    }

    /**
     * Removes the parent node from the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public boolean removeParent(ILNode parent) {
        return _parents.remove(parent);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public String toTreeString() {
        return String.format( "%s { %s }",
                toString(), getChild().toTreeString() );
    }
}
