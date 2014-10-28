package com.prophecy.processing.input.condition.base;

import com.prophecy.processing.input.condition.*;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public interface ICNodeVisitor {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the condition and-node.
     * @param cAnd The condition and-node.
     */
    void visit(final CAnd cAnd);

    /**
     * Visits the condition or-node.
     * @param cOr The condition or-node.
     */
    void visit(final COr cOr);

    /**
     * Visits the condition false-node.
     * @param cFalse The condition false-node.
     */
    void visit(final CFalse cFalse);

    /**
     * Visits the condition true-node.
     * @param cTrue The condition true-node.
     */
    void visit(final CTrue cTrue);

    /**
     * Visits the condition not-node.
     * @param cNot The condition not-node.
     */
    void visit(final CNot cNot);

    /**
     * Visits the condition op-node.
     * @param cOp The condition op-node.
     */
    void visit(final COp cOp);
}
