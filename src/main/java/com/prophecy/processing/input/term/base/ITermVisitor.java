package com.prophecy.processing.input.term.base;

import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.input.term.Value;

/**
 * Created by alpha_000 on 10.11.2014.
 */
public interface ITermVisitor<ParamT> {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Visits the attribute.
     * @param attribute The attribute.
     * @param param A possible parameter.
     */
    void visit(final Attribute attribute, ParamT param);

    /**
     * Visits the value.
     * @param value The value.
     * @param param A possible parameter.
     */
    void visit(final Value value, ParamT param);
}
