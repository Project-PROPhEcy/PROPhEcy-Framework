package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.utility.node.NNode;

import java.util.*;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public class LNNode extends NNode<LType, ILNode> implements ILNode {

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
     * Saves the default child node.
     */
    private final ILNode _defaultChild;


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
     * @param unique The unique state.
     * @param defaultChild The default child node.
     */
    public LNNode(final LType type, final boolean unique, final ILNode defaultChild) {
        super(type, unique);

        _defaultChild = defaultChild;

        // We need to add an
        // default child node.
        addChild(_defaultChild);
    }

    /**
     * Adds the node to the children if it's not already known.
     * @param child The node.
     * @param index The index.
     * @return The boolean value.
     */
    @Override
    public final boolean addChild(final ILNode child, int index) {

        // Remove the default child
        // node from the children.
        if(containsChild(_defaultChild)) {
            removeChild(_defaultChild);
            index--;
        }

        // Add this node to
        // the child parents.
        child.addParent(this);

        // Add child to the set.
        return super.addChild(child, index);
    }

    /**
     * Removes the node from the children if it's contained.
     * @param child The node.
     * @return The boolean value.
     */
    @Override
    public final boolean removeChild(final ILNode child) {

        if(super.removeChild(child)) {

            // The children list is
            // empty, so we need to add
            // the default child node again.
            if(getChildren().isEmpty())
                addChild(_defaultChild);

            // Remove this node from
            // the child parents.
            child.removeParent(this);

            return true;
        }

        return false;
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

        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < childCount(); i++) {
            if( i == 0 )  builder.append( String.format("{ %s }", getChild(i).toTreeString() ) );
            else builder.append( String.format( " and { %s }", getChild(i).toTreeString() ) );
        }
        return builder.toString();
    }
}
