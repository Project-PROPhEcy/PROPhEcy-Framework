package com.prophecy.processing.processor.contexts.lineage.tree.base;

import com.prophecy.processing.processor.contexts.lineage.tree.*;

/**
 * Created by alpha_000 on 27.10.2014.
 */
public interface ILNodeVisitor<ParamT> {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the lineage binary and-node.
     * @param lbAnd The lineage binary and-node.
     * @param param A possible parameter.
     */
    void visit(final LBAnd lbAnd, final ParamT param);

    /**
     * Visits the lineage binary or-node.
     * @param lbOr The lineage binary or-node.
     */
    void visit(final LBOr lbOr, final ParamT param);

    /**
     * Visits the lineage false-node.
     * @param lFalse The lineage false-node.
     */
    void visit(final LFalse lFalse, final ParamT param);

    /**
     * Visits the lineage true-node.
     * @param lTrue The lineage true-node.
     */
    void visit(final LTrue lTrue, final ParamT param);

    /**
     * Visits the lineage n-are and-node.
     * @param lnAnd The lineage n-are and-node.
     */
    void visit(final LNAnd lnAnd, final ParamT param);

    /**
     * Visits the lineage n-are or-node.
     * @param lnOr The lineage n-are or-node.
     */
    void visit(final LNOr lnOr, final ParamT param);

    /**
     * Visits the lineage unary not-node.
     * @param luNot The lineage unary not-node.
     */
    void visit(final LUNot luNot, final ParamT param);

    /**
     * Visits the lineage source-node.
     * @param lSource The lineage source-node.
     */
    void visit(final LSource lSource, final ParamT param);
}
