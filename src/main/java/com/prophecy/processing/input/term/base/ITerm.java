package com.prophecy.processing.input.term.base;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public interface ITerm extends ITermVisitable {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------

    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public String toSQLString();

} // interface ITerm
