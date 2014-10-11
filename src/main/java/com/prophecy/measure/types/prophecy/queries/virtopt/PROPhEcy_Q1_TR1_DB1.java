package com.prophecy.measure.types.prophecy.queries.virtopt;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.Map;

public class PROPhEcy_Q1_TR1_DB1 extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the query name.
     */
    @Override
    public String getName() {
        return "TR1_DB1_Q1";
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
        return "SELECT * FROM TR1_DB1_PROPHECY_Q1_INNER";
    }

    /**
     * Gets the additional input relation queries.
     */
    @Override
    public Map<Integer, String> getType3InputRelations() {
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
    public IFPNode getFormulaPattern(boolean factorize) {

        return FPNOr(factorize, HeadAttrs(
                        "custkey",
                        "name1",
                        "acctbal",
                        "name2",
                        "address",
                        "phone",
                        "comment1"
                ),
                        FPAnd( factorize,

                                FPNOr( factorize, HeadAttrs(
                                        "name2",
                                        "name1",
                                        "acctbal",
                                        "address",
                                        "phone",
                                        "comment1",
                                        "custkey"
                                ),
                                        FPAnd(factorize,

                                                FPSource(4, "nation", true, 0, HeadAttrs(
                                                        "nationkey",
                                                        "name2"
                                                )),
                                                FPSource(1, "customer", true, 0, HeadAttrs(
                                                        "name1",
                                                        "acctbal",
                                                        "address",
                                                        "phone",
                                                        "comment1",
                                                        "custkey",
                                                        "nationkey"
                                                ))
                                        )
                                ),
                                FPNOr(factorize, HeadAttrs(
                                        "custkey"
                                ),
                                        FPAnd(factorize,

                                                FPSource(2, "orders", true, 0, HeadAttrs(
                                                        "custkey",
                                                        "orderkey"
                                                )),
                                                FPSource(3, "lineitem", true, 0, HeadAttrs(
                                                        "orderkey"
                                                ))
                                        )
                                )
                        )
                );
    }
}
