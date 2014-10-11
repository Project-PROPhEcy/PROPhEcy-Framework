package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.HashMap;
import java.util.Map;

public class PROPhEcy_Q5_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    public String getName() {
        return "TR1_DB1_Q5";
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
        return "SELECT * FROM TR1_DB1_PROPHECY_Q5_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    public Map<Integer, String> getType3InputRelations() {
        return new HashMap<Integer, String>() {{
            put(1, "SELECT * FROM TR1_DB1_PROPHECY_Q5_S1");
            put(5, "SELECT * FROM TR1_DB1_PROPHECY_Q5_S5");
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
    public IFPNode getFormulaPattern(boolean factorize) {

        return FPAnd(factorize,
            FPSource(1, "ev_lineitem", true, 0, HeadAttrs("suppkey")),
            FPAnd(factorize,

                FPNOr(factorize, HeadAttrs(
                    "suppkey"
                ),
                    FPAnd(factorize,
                        FPSource(2, "ev_part", true, 0, HeadAttrs("partkey")),
                        FPSource(3, "ev_partsupp", true, 0, HeadAttrs("partkey", "suppkey"))
                    )
                ),
                FPNot(factorize,
                    FPNOr(factorize, HeadAttrs(
                        "suppkey"
                    ),
                        FPAnd(factorize,
                            FPSource(4, "ev_supplier", true, 0, HeadAttrs("suppkey", "nationkey")),
                            FPSource(5, "ev_customer", true, 0, HeadAttrs("nationkey"))
                        )
                    )
                )
            )
        );
    }
}
