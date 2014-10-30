package com.prophecy.processing.input.sql.base;

/**
 * Created by alpha_000 on 30.10.2014.
 */
public interface ISQLNodeVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    public void accept(final ISQLNodeVisitor visitor);
}
