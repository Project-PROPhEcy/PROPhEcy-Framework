/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.output;

import com.prophecy.processing.processor.contexts.calculation.Probability;
import com.prophecy.processing.processor.contexts.lineage.construction.GenTuple;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 29.07.2014.
 */
public class TupleResults {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private Map<GenTuple, Probability> _results
            = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the tuple results with their probabilities.
     */
    public Map<GenTuple, Probability> getResults() {
        return Collections.unmodifiableMap(_results);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Determines whether the resultset includes teh specific tuple.
     * @param tuple The tuple.
     * @return The boolean value.
     */
    public boolean contains(GenTuple tuple) {
        return _results.containsKey(tuple);
    }

    /**
     * Adds the tuple to the result set.
     * @param tuple The tuple.
     * @return The boolean value.
     */
    public boolean add(GenTuple tuple) {

        if(contains(tuple))
            return false;

        _results.put(tuple, new Probability());
        return true;
    }

    /**
     * Removes the tuple from the result set.
     * @param tuple The tuple.
     * @return The boolean value.
     */
    public boolean remove(GenTuple tuple) {

        if(!contains(tuple))
            return false;

        _results.remove(tuple);
        return true;
    }

    /**
     * Gets the probability for a specific tuple.
     * @param tuple The tuple.
     * @return The probability.
     */
    public Probability getProbability(GenTuple tuple) {

        if(!contains(tuple))
            return new Probability();

        return _results.get(tuple);
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    public String toString() {

        StringBuilder buffer = new StringBuilder();
        for(Map.Entry<GenTuple, Probability> entry: _results.entrySet()) {
            buffer.append(String.format("{ %s -> %s }\n", entry.getKey(), entry.getValue()));
        }

        return buffer.toString();
    }
}
