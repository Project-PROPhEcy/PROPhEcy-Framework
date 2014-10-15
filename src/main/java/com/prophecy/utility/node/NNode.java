package com.prophecy.utility.node;

import java.util.*;

/**
 * Created by alpha_000 on 29.06.2014.
 */
public class NNode<TypeT, NodeT> extends Node<TypeT> implements INNode<TypeT, NodeT> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private final boolean _unique;

    private final Map<NodeT, Integer> _contains
            = new HashMap<>();

    private final List<NodeT> _children
            = new ArrayList<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Determines whether child nodes
     * can only be added once.
     */
    @Override
    final public boolean isUnique() {
        return _unique;
    }


    /**
     * Gets the number of children.
     */
    @Override
    final public int childCount() {
        return _children.size();
    }


    /**
     * Gets all children from the node.
     */
    @Override
    final public List<NodeT> getChildren() {
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
    public NNode(final TypeT type, final boolean unique) {
        super(type);

        _unique = unique;
    }


    /**
     * Determines whether the node contains specific child node.
     * @param child The child node.
     * @return The boolean value.
     */
    @Override
    final public boolean containsChild(final NodeT child) {
        return _contains.containsKey(child);
    }


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @return The boolean value.
     */
    @Override
    final public boolean addChild(final NodeT child) {
        return addChild(child, _children.size());
    }


    /**
     * Adds the node to the children if it's not contained.
     * @param child The node.
     * @param index The index.
     * @return The boolean value.
     */
    @Override
    public boolean addChild(final NodeT child, final int index) {

        if(_unique && _contains
                .containsKey(child))
            return false;

        // Get the current number of
        // the specific child node.
        final int count = _contains
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
    public boolean removeChild(final NodeT child) {

        if(!_contains.containsKey(child))
            return false;

        // Get the current number of
        // the specific child node.
        final int count = _contains.get(child);

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
    final public boolean replaceChild(final NodeT oldChild, final NodeT newChild) {
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
    final public NodeT getChild(final int index) {
        return _children.get(index);
    }
}
