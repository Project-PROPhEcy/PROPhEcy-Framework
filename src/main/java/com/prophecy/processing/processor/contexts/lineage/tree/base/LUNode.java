package com.prophecy.processing.processor.contexts.lineage.tree.base;

import com.prophecy.processing.processor.contexts.lineage.tree.LFalse;

/**
 * Created by alpha_000 on 30.06.2014.
 */
public abstract class LUNode extends LNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private LNode _child = new LFalse();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the child node.
     */
    final public LNode getChild() {
        return _child;
    }

    /**
     * Sets the child node.
     */
    public void setChild(final LNode value) {
        _child = (value == null)
                ? new LFalse() : value;
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return String.format("%s { %s }",
                toString(), getChild().toTreeString() );
    }
}
