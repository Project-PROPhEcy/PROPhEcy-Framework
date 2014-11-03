package com.prophecy.processing.input.condition.base;

/**
 * Created by alpha_000 on 30.10.2014.
 */
public interface ICNodeVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    public <ParamT> void accept(final ICNodeVisitor<ParamT> visitor, ParamT param);
}
