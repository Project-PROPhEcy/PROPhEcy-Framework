package com.prophecy.utility.node;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public abstract class Node<TypeT> implements INode<TypeT> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the node type.
     */
    private final TypeT _type;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the node type.
     */
    @Override
    final public TypeT getType() {
        return _type;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param type The node type.
     */
    public Node(final TypeT type) {
        _type = type;
    }
}
