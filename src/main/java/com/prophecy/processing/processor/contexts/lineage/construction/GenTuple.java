/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;

import java.util.Arrays;
import java.util.List;

/**
 * Created by alpha_000 on 03.06.2014.
 */
public final class GenTuple {

    //----------------------------------------
    // Static Constants
    //----------------------------------------


    /**
     * Saves the empty gentuple constant.
     */
    public static GenTuple EMPTY = new GenTuple(
            new String[] { "$EMPTY$" });


    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Creates the gentuple from the specific
     * formula pattern node and domain tuple.
     * @param fp The formula pattern node.
     * @param d The domain tuple.
     * @param additions Optional keys.
     * @return The gentuple.
     */
    public static GenTuple From(final FPNode fp, final DomainTuple d, final Object... additions)
            throws Exception {

        final List<String> headAttrs
                = fp.getHeadAttrs();

        if(headAttrs.size() == 0
                && additions.length == 0)
            return GenTuple.EMPTY;

        final Object[] keys = new Object[
                headAttrs.size() + additions.length];

        int i = 0;

        for(; i < headAttrs.size(); i++)
            keys[i] = d.getAttr(headAttrs.get(i));

        System.arraycopy(additions, 0,
                keys, i, additions.length);

        return new GenTuple(keys);
    }


    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the gentuple keys.
     */
    private final Object[] _keys;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the gentuple keys.
     */
    public final Object[] getKeys() {
        return _keys;
    }


    /**
     * Gets the hash code for the event.
     */
    @Override
    public final int hashCode() {
        return Arrays.hashCode(_keys);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param keys The keys.
     */
    public GenTuple(final Object[] keys) {
        _keys = keys;
    }


    /**
     * Determines whether the two gentuples are equal.
     * @param o The other gentuple.
     * @return The boolean value.
     */
    @Override
    public final boolean equals(final Object o) {

        if (this == o) return true;
        if (o == null
                || getClass() != o.getClass())
            return false;

        final GenTuple genTuple = (GenTuple) o;

        // Probably incorrect - comparing
        // Object[] arrays with Arrays.equals
        return Arrays.equals(_keys, genTuple._keys);
    }

    /**
     * Returns teh string representation.
     * @return The string representation.
     */
    public final String toString() {

        final StringBuilder buffer = new StringBuilder("(");
        for(int i = 0; i < _keys.length; i++) {
            buffer.append((i == 0)? _keys[i] : " | " + _keys[i]);
        }
        buffer.append(")");
        return buffer.toString();
    }
}
