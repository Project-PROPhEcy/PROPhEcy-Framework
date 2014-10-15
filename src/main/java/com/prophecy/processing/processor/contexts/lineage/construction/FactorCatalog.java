package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.processor.contexts.lineage.tree.ILNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 04.07.2014.
 */
public final class FactorCatalog {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private final Map<GenTuple, ILNode> _nodes
            = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the number of nodes in the node catalog.
     */
    public final int size() {
        return _nodes.size();
    }

    /**
     * Gets the already known nodes in this catalog.
     */
    public final Map<GenTuple, ILNode> getNodes() {
        return Collections.unmodifiableMap(_nodes);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Determines whether the key is known
     * in the factor catalog.
     * @param key The gentuple key.
     * @return The boolean value.
     */
    public final boolean contains(final GenTuple key) {
        return _nodes.containsKey(key);
    }


    /**
     * Gets the node for the specific key.
     * @param key The gentuple key.
     * @return The node.
     */
    public final ILNode get(final GenTuple key) {
        return _nodes.get(key);
    }


    /**
     * Puts the node into the factor catalog
     * with the specific key.
     * @param key The gentuple key.
     * @param node The node
     * @return The node.
     */
    public final ILNode put(final GenTuple key, final ILNode node) {
        _nodes.putIfAbsent(key, node);
        return _nodes.get(key);
    }
}
