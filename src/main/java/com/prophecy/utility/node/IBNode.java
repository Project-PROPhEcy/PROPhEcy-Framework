package com.prophecy.utility.node;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public interface IBNode<TypeT, NodeT> extends INode<TypeT> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the left child node.
     */
    public NodeT getLeftChild();


    /**
     * Sets the left child node.
     */
    public void setLeftChild(final NodeT value);


    /**
     * Gets the right child node.
     */
    public NodeT getRightChild();


    /**
     * Sets the right child node.
     */
    public void setRightChild(final NodeT value);
}