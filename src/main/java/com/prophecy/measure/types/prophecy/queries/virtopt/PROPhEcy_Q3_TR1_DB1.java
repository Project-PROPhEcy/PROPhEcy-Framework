package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.HashMap;
import java.util.Map;

public class PROPhEcy_Q3_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    public String getName() {
        return "TR1_DB1_Q3";
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
        return "SELECT * FROM TR1_DB1_PROPHECY_Q3_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    public Map<Integer, String> getType3InputRelations() {
        return new HashMap<Integer, String>() {{
            put(3, "SELECT * FROM TR1_DB1_PROPHECY_Q3_S3");
            put(4, "SELECT * FROM TR1_DB1_PROPHECY_Q3_S4");
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
            FPNOr(factorize, HeadAttrs(
                "suppkey"
            ),
                FPAnd(factorize,
                    FPAnd(factorize,
                        FPSource(1, "part", true, 0, HeadAttrs("partkey")),
                        FPSource(2, "partsupp", true, 0, HeadAttrs("partkey", "suppkey"))
                    ),
                    FPSource(3, "lineitem", true, 0, HeadAttrs("partkey"))
                )
            ),
            FPNOr(factorize, HeadAttrs(
                "suppkey"
            ),
                FPAnd(factorize,
                    FPSource(5, "supplier", true, 0, HeadAttrs("suppkey", "nationkey")),
                    FPSource(4, "customer", true, 0, HeadAttrs("nationkey"))
                )
            )
        );
    }
}
