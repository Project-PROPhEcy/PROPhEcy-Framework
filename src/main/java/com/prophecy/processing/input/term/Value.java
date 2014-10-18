package com.prophecy.processing.input.term;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class Value implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the concrete value.
     */
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
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public final String toSQLString() {
        return getInner().toString();
    }
}
