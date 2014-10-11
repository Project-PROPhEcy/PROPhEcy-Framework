package com.prophecy.utility;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by alpha_000 on 29.06.2014.
 */
public class ListUtils {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * This function is used to remove duplicates from a given list.
     * @param list The list.
     * @return The same list without duplicates.
     */
    public static <T> List<T> RemoveDuplicates(List<T> list)
    {
        Set<T> uniqueEntries = new HashSet<T>();
        for (Iterator<T> it = list.iterator(); it.hasNext(); ) {
            T element = it.next();
            if (!uniqueEntries.add(element))
                it.remove();
        }

        return list;
    }

    /**
     * This function is used to group a list.
     * @param list The list.
     * @param group The group function.
     * @return The resulting map.
     */
    public static <S, T> Map<S, List<T>> GroupBy(Collection<T> list, Function<T, S> group) {

        Map<S, List<T>> groups = new HashMap<>();
        for(T item: list) {

            S key = group.apply(item);
            if(!groups.containsKey(key))
                groups.put(key, new ArrayList<>());

            groups.get(key).add(item);
        }

        return groups;
    }

    /**
     * Used to fold left the list.
     * @param f The fold function.
     * @param z The initial value.
     * @param xs The list.
     * @return The result.
     */
    public static <A, B> A FoldLeft(A z, Iterable<B> xs, BiFunction<A, B, A> f)
    {
        A p = z;
        for (B x: xs) {
            p = f.apply(p, x);
        }
        return p;
    }
}
