package com.prophecy.utility;

/**
 * Created by alpha_000 on 03.11.2014.
 */
public class Reference<ValueT> {

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets or sets the inner value.
     */
    public ValueT value;

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param value The inner value.
     */
    public Reference(ValueT value) {
        this.value = value;
    }
}
