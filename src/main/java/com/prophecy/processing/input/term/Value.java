package com.prophecy.processing.input.term;

import com.prophecy.processing.input.term.base.ITerm;
import com.prophecy.processing.input.term.base.ITermVisitor;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class Value implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final Object _inner;

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    /**
     * Gets the concrete value.
     */
    public final Object getInner() {
        return _inner;
    }

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Constructor
     * @param inner The concrete value.
     */
    public Value(final Object inner) {
        _inner = inner;
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    @Override
    public <ParamT> void accept(ITermVisitor<ParamT> visitor, ParamT param) {
        visitor.visit(this, param);
    }

    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public final String toSQLString() {
        return getInner().toString();
    }
}
