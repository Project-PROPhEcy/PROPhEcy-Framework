package com.prophecy.processing.input.term;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class Attribute implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the attribute name.
     */
    private final String _name;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the attribute name.
     */
    public final String getName() {
        return _name;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param name The attribute name.
     */
    public Attribute(final String name) {
        _name = name;
    }


    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public final String toSQLString() {
        return getName();
    }
}
