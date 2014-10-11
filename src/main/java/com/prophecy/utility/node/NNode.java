package com.prophecy.utility.node;

import java.util.*;

/**
 * Created by alpha_000 on 29.06.2014.
 */
public class NNode<TypeT, NodeT> extends Node<TypeT> implements INNode<TypeT, NodeT> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the unique state.
     */
    private boolean _unique = false;


    /**
     * Saves how many times a child node has been added.
     */
    private Map<NodeT, Integer> _contains
            = new HashMap<>();


    /**
     * Saves the available node children.
     */
    private List<NodeT> _children
            = new ArrayList<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Determines whether child nodes
     * can only be added once.
     */
    @Override
    public boolean isUnique() {
        return _unique;
    }


    /**
     * Gets the number of children.
     */
    @Override
    public int childCount() {
        return _children.size();
    }


    /**
     * Gets all children from the node.
     */
    @Override
    public List<NodeT> getChildren() {
        return Collections
                .unmodifiableList(_children);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param type The node type.
     * @param unique Child nodes can only be added once.
     */
    public NNode(TypeT type, boolean unique) {
        super(type);

        _unique = unique;
    }


    /**
     * Determines whether the node contains specific child node.
     * @param child The child node.
     * @return The boolean value.
     */
    @Override
    public boolean containsChild(NodeT child) {
        return _contains.containsKey(child);
    }


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @return The boolean value.
     */
    @Override
    public boolean addChild(NodeT child) {
        return addChild(child, _children.size());
    }


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @param index The index.
     * @return The boolean value.
     */
    @Override
    public boolean addChild(NodeT child, int index) {

        if(_unique && _contains
                .containsKey(child))
            return false;

        // Get the current number of
        // the specific child node.
        int count = _contains
                .getOrDefault(child, 0);

        // Update the number of the
        // specific child node.
        _contains.put(child, count + 1);
        _children.add(index, child);

        return true;
    }


    /**
     * Removes the node from the children if it's contained.
     * @param child The node.
     * @return The boolean value.
     */
    @Override
    public boolean removeChild(NodeT child) {

        if(!_contains.containsKey(child))
            return false;

        // Get the current number of
        // the specific child node.
        int count = _contains.get(child);

        // If the count is 0 then delete
        // the child node completely.
        if(count - 1 == 0)
            _contains.remove(child);
        else
            _contains.put(child, count - 1);

        return _children.remove(child);
    }


    /**
     * Replaces the old child node with the new child node.
     * @param oldChild The old child node.
     * @param newChild The new child node.
     * @return The boolean value.
     */
    @Override
    public boolean replaceChild(NodeT oldChild, NodeT newChild) {
        return addChild(newChild,
            _children.indexOf(oldChild))
                && removeChild(oldChild);
    }


    /**
     * Gets the child node or null from the specific index.
     * @param index The index.
     * @return The child node or null.
     */
    @Override
    public NodeT getChild(int index) {
        return _children.get(index);
    }
}
