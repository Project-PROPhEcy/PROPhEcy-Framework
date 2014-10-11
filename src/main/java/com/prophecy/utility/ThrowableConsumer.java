package com.prophecy.utility;

/**
 * Created by alpha_000 on 04.09.2014.
 */
@FunctionalInterface
public interface ThrowableConsumer<T, E extends Exception> {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Executes the consumer.
     * @param t The input.
     */
    void apply(T t) throws E;
}
