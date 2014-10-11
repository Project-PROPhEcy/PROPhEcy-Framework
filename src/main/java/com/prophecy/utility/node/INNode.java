package com.prophecy.utility.node;

import java.util.List;

/**
 * Created by alpha_000 on 26.05.2014.
 */
public interface INNode<TypeT, NodeT> extends INode<TypeT> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Determines whether child nodes
     * can only be added once.
     */
    public boolean isUnique();


    /**
     * Gets the number of children.
     */
    public int childCount();


    /**
     * Gets all children from the node.
     */
    public List<NodeT> getChildren();


    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * Determines whether the node contains specific child node.
     * @param child The child node.
     * @return The boolean value.
     */
    public boolean containsChild(NodeT child);


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @return The boolean value.
     */
    public boolean addChild(NodeT child);


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @param index The index.
     * @return The boolean value.
     */
    public boolean addChild(NodeT child, int index);


    /**
     * Removes the node from the children if it's contained.
     * @param child The node.
     * @return The boolean value.
     */
    public boolean removeChild(NodeT child);


    /**
     * Gets the child node or null from the specific index.
     * @param index The index.
     * @return The child node or null.
     */
    public NodeT getChild(int index);


    /**
     * Replaces the old child node with the new child node.
     * @param oldChild The old child node.
     * @param newChild The new child node.
     * @return The boolean value.
     */
    public boolean replaceChild(NodeT oldChild, NodeT newChild);
}
