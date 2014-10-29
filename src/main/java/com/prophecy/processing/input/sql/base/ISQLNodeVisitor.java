package com.prophecy.processing.input.sql.base;

import com.prophecy.processing.input.sql.*;

/**
 * Created by alpha_000 on 29.10.2014.
 */
public interface ISQLNodeVisitor {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the sql root-node.
     * @param sqlRoot The sql root-node.
     */
    void visit(final SQLRoot sqlRoot);

    /**
     * Visits the sql inner-join-node.
     * @param sqlIJoin The sql inner-join-node.
     */
    void visit(final SQLIJoin sqlIJoin);

    /**
     * Visits the sql natural-join-node.
     * @param sqlNJoin The sql natural-join-node.
     */
    void visit(final SQLNJoin sqlNJoin);

    /**
     * Visits the sql union-node.
     * @param sqlUnion The sql union-node.
     */
    void visit(final SQLUnion sqlUnion);

    /**
     * Visits the sql minus-node.
     * @param sqlMinus The sql minus-node.
     */
    void visit(final SQLMinus sqlMinus);

    /**
     * Visits the sql projection-node.
     * @param sqlProjection The sql projection-node.
     */
    void visit(final SQLProjection sqlProjection);

    /**
     * Visits the sql selection-node.
     * @param sqlSelection The sql selection-node.
     */
    void visit(final SQLSelection sqlSelection);

    /**
     * Visits the sql relation-node.
     * @param sqlRelation The sql relation-node.
     */
    void visit(final SQLRelation sqlRelation);
}
