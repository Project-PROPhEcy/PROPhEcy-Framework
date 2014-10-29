package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.input.condition.base.CNode;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public abstract class FPUNode extends FPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private FPNode _child = null;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the child node.
     */
    final public FPNode getChild() {
        return _child;
    }

    /**
     * Sets the child node.
     */
    public void setChild(final FPNode value) {
        _child = value;
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     */
    public FPUNode(boolean factorize, CNode condition) {
        super(factorize, condition);
    }

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
