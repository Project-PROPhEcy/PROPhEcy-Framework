package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.HashMap;
import java.util.Map;

public final class PROPhEcy_Q6_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    final public String getName() {
        return "TR1_DB1_Q6";
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
        return "SELECT * FROM TR1_DB1_PROPHECY_Q6_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    final public Map<Integer, String> getType3InputRelations() {
        return new HashMap<Integer, String>() {{
            put(1, "SELECT * FROM TR1_DB1_PROPHECY_Q6_S1");
        }};
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
    final public IFPNode getFormulaPattern(final boolean factorize) {
        return FPAnd(factorize,
            FPNOr(factorize, HeadAttrs(
                "suppkey"
            ),
                FPAnd(factorize,
                    FPSource(1, "ev_customer", true, 0, HeadAttrs("nationkey")),
                    FPSource(2, "ev_supplier", true, 0, HeadAttrs("suppkey", "nationkey"))
                )
            ),
            FPNot(factorize,

                FPOr(factorize,
                    FPNOr(factorize, HeadAttrs(
                        "suppkey"
                    ),
                        FPAnd(factorize,
                            FPSource(3, "ev_part", true, 0, HeadAttrs("partkey")),
                            FPSource(4, "ev_partsupp", true, 0, HeadAttrs("partkey", "suppkey"))
                        )
                    ),
                    FPNOr(factorize, HeadAttrs(
                        "suppkey"
                    ),
                        FPAnd(factorize,
                            FPSource(5, "ev_lineitem", true, 0, HeadAttrs("suppkey", "orderkey")),
                            FPSource(6, "ev_orders", true, 0, HeadAttrs("orderkey"))
                        )
                    )
                )
            )
        );
    }
}
