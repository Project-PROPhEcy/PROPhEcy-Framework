package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.processor.contexts.formulapattern.tree.*;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public interface IFPNodeVisitor {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the formula pattern and-node.
     * @param fpAnd The formula pattern and-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    void visit(final FPAnd fpAnd, LNode lNode, final DomainTuple d, final int sourceId);

    /**
     * Visits the formula pattern or-node.
     * @param fpOr The formula pattern or-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    void visit(final FPOr fpOr, LNode lNode, final DomainTuple d, final int sourceId);

    /**
     * Visits the formula pattern n-are or-node.
     * @param fpNOr The formula pattern n-are or-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    void visit(final FPNOr fpNOr, LNode lNode, final DomainTuple d, final int sourceId);

    /**
     * Visits the formula pattern not-node.
     * @param fpNot The formula pattern not-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    void visit(final FPNot fpNot, LNode lNode, final DomainTuple d, final int sourceId);

    /**
     * Visits the formula pattern source-node.
     * @param fpSource The formula pattern source-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    void visit(final FPSource fpSource, LNode lNode, final DomainTuple d, final int sourceId);
}
