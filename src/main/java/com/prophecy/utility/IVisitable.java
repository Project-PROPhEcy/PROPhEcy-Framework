package com.prophecy.utility;

/**
 * Created by alpha_000 on 27.10.2014.
 */
public interface IVisitable<VisitorT> {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    public void accept(final VisitorT visitor);
}
