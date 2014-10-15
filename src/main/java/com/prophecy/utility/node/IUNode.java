package com.prophecy.utility.node;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public interface IUNode<TypeT, NodeT> extends INode<TypeT> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the child node.
     */
    public NodeT getChild();


    /**
     * Sets the child node.
     */
    public void setChild(final NodeT value);
}
