package com.prophecy.processing.input.term.base;

/**
 * Created by alpha_000 on 10.11.2014.
 */
public interface ITermVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    public <ParamT> void accept(final ITermVisitor<ParamT> visitor, ParamT param);
}
