package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;

import java.util.Map;

public final class PROPhEcy_Q0_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    final public String getName() {
        return "TR1_DB1_Q0";
    }

    /**
     * Gets the query typing.
     */
    @Override
    final public String getTyping() {
        return "-";
    }

    /**
     * Gets the input relation query.
     */
    @Override
    final public String getInputRelation() {
        return "SELECT * FROM TR1_DB1_PROPHECY_Q0_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    final public Map<Integer, String> getType3InputRelations() {
        return null;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Gets the formula pattern.
     * @param factorize Use factorization for the lineage construction.
     * @return The formula pattern.
     */
    @Override
    final public FPNode getFormulaPattern(final boolean factorize) {

        return FPAnd( factorize,
                FPNOr( factorize, HeadAttrs(
                        "orderkey",
                        "orderdate",
                        "shippriority"
                ),
                        FPAnd(factorize,
                                FPSource(1, "customer", true, 0, HeadAttrs(
                                        "custkey"
                                )),
                                FPSource(2, "orders", true, 0, HeadAttrs(
                                        "custkey",
                                        "orderkey",
                                        "orderdate",
                                        "shippriority"
                                ))
                        )
                ),
                FPSource(3, "lineitem", true, 0,  HeadAttrs(
                    "orderkey"
                ))
        );
    }
}
