package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.input.condition.base.CNode;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public abstract class FPBNode extends FPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private FPNode _leftChild = null;
    private FPNode _rightChild = null;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the left child node.
     */
    final public FPNode getLeftChild() {
        return _leftChild;
    }

    /**
     * Sets the left child node.
     */
    final public void setLeftChild(final FPNode value) {
        _leftChild = value;
    }

    /**
     * Gets the right child node.
     */
    final public FPNode getRightChild() {
        return _rightChild;
    }

    /**
     * Sets the right child node.
     */
    final public void setRightChild(final FPNode value) {
        _rightChild = value;
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     */
    public FPBNode(boolean factorize, CNode condition) {
        super(factorize, condition);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return String.format("{ %s } %s { %s }"
                , getRightChild().toTreeString()
                , toString()
                , getRightChild().toTreeString());
    }
}
