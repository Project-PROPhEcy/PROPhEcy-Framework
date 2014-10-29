package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.processor.contexts.formulapattern.tree.*;

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
     */
    void visit(final FPAnd fpAnd);

    /**
     * Visits the formula pattern or-node.
     * @param fpOr The formula pattern or-node.
     */
    void visit(final FPOr fpOr);

    /**
     * Visits the formula pattern n-are or-node.
     * @param fpNOr The formula pattern n-are or-node.
     */
    void visit(final FPNOr fpNOr);

    /**
     * Visits the formula pattern not-node.
     * @param fpNot The formula pattern not-node.
     */
    void visit(final FPNot fpNot);

    /**
     * Visits the formula pattern source-node.
     * @param fpSource The formula pattern source-node.
     */
    void visit(final FPSource fpSource);
}
