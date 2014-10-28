package com.prophecy.processing.processor.contexts.lineage.tree.base;

import com.prophecy.processing.processor.contexts.lineage.tree.LFalse;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public abstract class LBNode extends LNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private LNode _leftChild = new LFalse();
    private LNode _rightChild = new LFalse();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the left child node.
     */
    final public LNode getLeftChild() {
        return _leftChild;
    }

    /**
     * Sets the left child node.
     */
    final public void setLeftChild(final LNode value) {
        _leftChild = (value == null)
                ? new LFalse() : value;
    }

    /**
     * Gets the right child node.
     */
    final public LNode getRightChild() {
        return _rightChild;
    }

    /**
     * Sets the right child node.
     */
    final public void setRightChild(final LNode value) {
        _rightChild = (value == null)
                ? new LFalse() : value;
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
