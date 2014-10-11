package com.prophecy.utility;

/**
 * Created by alpha_000 on 04.09.2014.
 */
@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Exception> {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Executes the function.
     * @param t The input.
     * @return The result.
     */
    R apply(T t) throws E;
}
