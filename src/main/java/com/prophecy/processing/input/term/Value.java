package com.prophecy.processing.input.term;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public class Value implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the concrete value.
     */
    private Object _inner = null;


    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Gets the concrete value.
     */
    public Object getInner() {
        return _inner;
    }


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Constructor
     * @param inner The concrete value.
     */
    public Value(Object inner) {
        _inner = inner;
    }


    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public String toSQLString() {
        return getInner().toString();
    }
}
