package com.prophecy.utility.node;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public class UNode<TypeT, NodeT> extends Node<TypeT> implements IUNode<TypeT, NodeT> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the child node.
     */
    private NodeT _child = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the child node.
     */
    @Override
    public NodeT getChild() {
        return _child;
    }


    /**
     * Sets the child node.
     */
    @Override
    public void setChild(NodeT value) {
        _child = value;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param type The node type.
     */
    public UNode(TypeT type) {
        super(type);
    }
}
