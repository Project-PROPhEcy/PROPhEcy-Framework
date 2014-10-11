package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.HashMap;
import java.util.Map;

public class PROPhEcy_Q2_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    public String getName() {
        return "TR1_DB1_Q2";
    }

    /**
     * Gets the query typing.
     */
    @Override
    public String getTyping() {
        return "-";
    }


    /**
     * Gets the input relation query.
     */
    @Override
    public String getInputRelation() {
        return "SELECT * FROM TR1_DB1_PROPHECY_Q2_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    public Map<Integer, String> getType3InputRelations() {
        return new HashMap<Integer, String>() {{
            put(1, "SELECT * FROM TR1_DB1_PROPHECY_Q2_S1");
            put(2, "SELECT * FROM TR1_DB1_PROPHECY_Q2_S2");
            put(5, "SELECT * FROM TR1_DB1_PROPHECY_Q2_S5");
        }};
    }

    /**
     * Gets the formula pattern.
     *
     * @param factorize Use factorization for the lineage construction.
     * @return The formula pattern.
     */
    @Override
    public IFPNode getFormulaPattern(boolean factorize) {

        return FPNOr(factorize, HeadAttrs(
            "nationkey",
            "suppkey"
        ),
            FPAnd(factorize,
                FPAnd(factorize,
                    FPNOr(factorize, HeadAttrs(
                        "suppkey"
                    ),
                        FPAnd(factorize,
                            FPSource(1, "partsupp", true, 0, HeadAttrs("partkey")),
                            FPSource(2, "lineitem", true, 0, HeadAttrs("partkey", "suppkey"))
                        )
                    ),
                    FPSource(3, "supplier", true, 0, HeadAttrs("suppkey", "nationkey"))
                ),
                FPNOr(factorize, HeadAttrs(
                    "nationkey"
                ),
                    FPAnd(factorize,
                        FPSource(5, "orders", true, 0, HeadAttrs("custkey")),
                        FPSource(4, "customer", true, 0, HeadAttrs("custkey", "nationkey"))
                    )
                )
            )
        );
    }
}
