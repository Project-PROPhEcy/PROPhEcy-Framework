package com.prophecy.utility;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by alpha_000 on 29.06.2014.
 */
public final class ListUtils {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * This function is used to remove duplicates from a given list.
     * @param list The list.
     * @return The same list without duplicates.
     */
    public static <T> List<T> RemoveDuplicates(final List<T> list)
    {
        T element = null;
        final Set<T> uniqueEntries = new HashSet<T>();
        for (final Iterator<T> it = list.iterator(); it.hasNext(); ) {
            element = it.next();
            if (!uniqueEntries.add(element))
                it.remove();
        }

        return list;
    }

    /**
     * Used to fold left the list.
     * @param f The fold function.
     * @param z The initial value.
     * @param xs The list.
     * @return The result.
     */
    public static <A, B> A FoldLeft(final A z, final Iterable<B> xs, final BiFunction<A, B, A> f)
    {
        A p = z;
        for (final B x: xs) {
            p = f.apply(p, x);
        }
        return p;
    }
}
