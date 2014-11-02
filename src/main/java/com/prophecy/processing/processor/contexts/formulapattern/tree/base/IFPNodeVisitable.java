package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

/**
 * Created by alpha_000 on 30.10.2014.
 */
public interface IFPNodeVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId current The source id.
     */
    public void accept(final IFPNodeVisitor visitor, LNode lNode, final DomainTuple d, final int sourceId);
}
