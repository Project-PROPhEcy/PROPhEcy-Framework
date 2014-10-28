package com.prophecy.processing.input.condition.base;

import com.prophecy.processing.input.condition.CFalse;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public abstract class CBNode extends CNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private CNode _leftChild = new CFalse();
    private CNode _rightChild = new CFalse();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the left child node.
     */
    final public CNode getLeftChild() {
        return _leftChild;
    }

    /**
     * Sets the left child node.
     */
    final public void setLeftChild(final CNode value) {
        _leftChild = (value == null)
                ? new CFalse() : value;
    }

    /**
     * Gets the right child node.
     */
    final public CNode getRightChild() {
        return _rightChild;
    }

    /**
     * Sets the right child node.
     */
    final public void setRightChild(final CNode value) {
        _rightChild = (value == null)
                ? new CFalse() : value;
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
