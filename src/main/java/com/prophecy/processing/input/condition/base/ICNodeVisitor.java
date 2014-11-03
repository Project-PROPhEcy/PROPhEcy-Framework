package com.prophecy.processing.input.condition.base;

import com.prophecy.processing.input.condition.*;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public interface ICNodeVisitor<ParamT> {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the condition and-node.
     * @param cAnd The condition and-node.
     * @param param A possible parameter.
     */
    void visit(final CAnd cAnd, ParamT param);

    /**
     * Visits the condition or-node.
     * @param cOr The condition or-node.
     * @param param A possible parameter.
     */
    void visit(final COr cOr, ParamT param);

    /**
     * Visits the condition false-node.
     * @param cFalse The condition false-node.
     * @param param A possible parameter.
     */
    void visit(final CFalse cFalse, ParamT param);

    /**
     * Visits the condition true-node.
     * @param cTrue The condition true-node.
     * @param param A possible parameter.
     */
    void visit(final CTrue cTrue, ParamT param);

    /**
     * Visits the condition not-node.
     * @param cNot The condition not-node.
     * @param param A possible parameter.
     */
    void visit(final CNot cNot, ParamT param);

    /**
     * Visits the condition op-node.
     * @param cOp The condition op-node.
     * @param param A possible parameter.
     */
    void visit(final COp cOp, ParamT param);
}
