package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.processor.contexts.lineage.tree.ILNode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 04.07.2014.
 */
public class FactorCatalog {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private Map<GenTuple, ILNode> _nodes
            = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the number of nodes in the node catalog.
     */
    public int size() {
        return _nodes.size();
    }

    /**
     * Gets the already known nodes in this catalog.
     */
    public Map<GenTuple, ILNode> getNodes() {
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
    public boolean contains(GenTuple key) {
        return _nodes.containsKey(key);
    }


    /**
     * Gets the node for the specific key.
     * @param key The gentuple key.
     * @return The node.
     */
    public ILNode get(GenTuple key) {
        return _nodes.get(key);
    }


    /**
     * Puts the node into the factor catalog
     * with the specific key.
     * @param key The gentuple key.
     * @param node The node
     * @return The node.
     */
    public ILNode put(GenTuple key, ILNode node) {
        _nodes.putIfAbsent(key, node);
        return _nodes.get(key);
    }
}
