package com.prophecy.utility.node;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public class BNode<TypeT, NodeT> extends Node<TypeT> implements IBNode<TypeT, NodeT> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the left child node.
     */
    private NodeT _leftChild = null;


    /**
     * Saves the right child node.
     */
    private NodeT _rightChild = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the left child node.
     */
    @Override
    public NodeT getLeftChild() {
        return _leftChild;
    }


    /**
     * Sets the left child node.
     */
    @Override
    public void setLeftChild(NodeT value) {
        _leftChild = value;
    }


    /**
     * Gets the right child node.
     */
    @Override
    public NodeT getRightChild() {
        return _rightChild;
    }


    /**
     * Sets the right child node.
     */
    @Override
    public void setRightChild(NodeT value) {
        _rightChild = value;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param type The node type.
     */
    public BNode(TypeT type) {
        super(type);
    }
}
