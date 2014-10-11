package com.prophecy.measure.types.prophecy.queries.tests;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.processor.contexts.formulapattern.tree.IFPNode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 15.09.2014.
 */
public final class PROPhEcy_Q_P1_Tract extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    public final String getName() {
        return "Paper_Q1";
    }


    /**
     * Gets the query typing.
     */
    @Override
    public final String getTyping() {
        return "tract";
    }


    /**
     * Gets the input relation query.
     */
    @Override
    public final String getInputRelation() {
        return "SELECT * FROM PROPHECY_Q_P1_VO_EVENT";
    }


    /**
     * Gets the additional input relation queries.
     */
    @Override
    public final Map<Integer, String> getType3InputRelations() {
        return new HashMap<>();
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

        return FPAnd( factorize,
            FPSource( 1, "R1", true, 0, HeadAttrs( "A" ) ),
            FPNOr( factorize, HeadAttrs( "A" ),
                FPAnd( factorize,
                    FPSource( 2, "R2", true, 0, HeadAttrs( "A", "B" ) ),
                    FPSource( 3, "R3", true, 0, HeadAttrs( "A", "B" ) )
                )
            )
        );
    }
}
