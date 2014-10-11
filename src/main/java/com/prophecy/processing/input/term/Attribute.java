package com.prophecy.processing.input.term;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public class Attribute implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the attribute name.
     */
    private String _name = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the attribute name.
     */
    public String getName() {
        return _name;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param name The attribute name.
     */
    public Attribute(String name) {
        _name = name;
    }


    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public String toSQLString() {
        return getName();
    }
}
