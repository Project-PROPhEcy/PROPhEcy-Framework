package com.prophecy.utility.function;

/**
 * Created by alpha_000 on 28.09.2014.
 */
@FunctionalInterface
public interface ThrowableRunnable<E extends Exception> {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Executes the function.
     */
    void apply() throws E;
}

