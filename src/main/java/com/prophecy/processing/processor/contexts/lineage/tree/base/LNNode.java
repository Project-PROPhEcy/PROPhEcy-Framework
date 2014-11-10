package com.prophecy.processing.processor.contexts.lineage.tree.base;

import com.prophecy.processing.processor.contexts.lineage.tree.LFalse;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public abstract class LNNode extends LNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final LNode _defaultChild = new LFalse();
    private final Set<LNode> _children
            = new LinkedHashSet<>();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the number of children.
     */
    final public int size() {
        return _children.size();
    }

    /**
     * Gets all children from the node.
     */
    final public Set<LNode> children() {
        return Collections
                .unmodifiableSet(_children);
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     */
    public LNNode() {
        insert(_defaultChild);
    }

    /**
     * Determines whether the node contains specific child node.
     * @param child The child node.
     * @return The boolean value.
     */
    final public boolean contains(final LNode child) {
        return _children.contains(child);
    }

    /**
     * Inserts the node to the children if it's not contained.
     * @param child The node.
     * @return The boolean value.
     */
    final public boolean insert(final LNode child) {
        if(contains(_defaultChild))
            _children.remove(_defaultChild);
        return _children.add(child);
    }

    /**
     * Removes the node from the children if it's contained.
     * @param child The node.
     * @return The boolean value.
     */
    public boolean remove(final LNode child) {
        return (_children.remove(child))
                && ((size() != 0) || insert(_defaultChild));
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return _children.stream().map(
                (LNode child) -> String.format("{ %s }", child.toTreeString()))
                    .collect(Collectors.joining(String.format(" %s ", toString())));
    }
}
