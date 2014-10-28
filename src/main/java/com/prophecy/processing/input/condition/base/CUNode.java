package com.prophecy.processing.input.condition.base;

import com.prophecy.processing.input.condition.CFalse;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public abstract class CUNode extends CNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private CNode _child = new CFalse();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the child node.
     */
    final public CNode getChild() {
        return _child;
    }

    /**
     * Sets the child node.
     */
    public void setChild(final CNode value) {
        _child = (value == null)
                ? new CFalse() : value;
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
