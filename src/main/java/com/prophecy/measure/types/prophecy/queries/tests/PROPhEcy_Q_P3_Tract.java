package com.prophecy.measure.types.prophecy.queries.tests;

import com.prophecy.measure.types.prophecy.PROPhEcyMeasureInput;
import com.prophecy.processing.input.condition.COpType;
import com.prophecy.processing.processor.contexts.formulapattern.tree.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 15.09.2014.
 */
public final class PROPhEcy_Q_P3_Tract extends PROPhEcyMeasureInput {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    @Override
    final public String getName() {
        return "Paper_Q3";
    }


    /**
     * Gets the query typing.
     */
    @Override
    final public String getTyping() {
        return "tract";
    }


    /**
     * Gets the input relation query.
     */
    @Override
    final public String getInputRelation() {
        return "SELECT * FROM PROPHECY_Q_P3_VO_EVENT";
    }


    /**
     * Gets the additional input relation queries.
     */
    @Override
    final public Map<Integer, String> getType3InputRelations() {
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

        return FPAnd(factorize,
                FPSource(1, "R1", true, 0, HeadAttrs("A")),
                FPNOr(factorize, HeadAttrs("A"),

                    FPSource(2, "R3", true, 0, HeadAttrs("A", "B"),
                        COp(Attribute("A"), COpType.Unequal, Value(4)))
                )
        );
    }
}
