/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing;

import com.prophecy.utility.ThrowableRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by alpha_000 on 07.09.2014.
 */
public final class TaskInfo {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the time measures for various scopes.
     */
    private final Map<String, Double> _measures = new HashMap<>();


    /**
     * Saves the infos for various scopes.
     */
    private final Map<String, Object> _infos = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the available time scopes.
     */
    public final Set<String> getMeasureScopes() {
        return _measures.keySet();
    }


    /**
     * Gets the available value scopes.
     */
    public final Set<String> getInfoScopes() {
        return _infos.keySet();
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Sets new info data for the specific scope.
     * @param scope The scope.
     * @param info The info data.
     */
    final public void setInfo(final String scope, final Object info) {
        _infos.put(scope, info);
    }

    /**
     * Unsets the info data for the specific scope.
     * @param scope The scope.
     */
    final public void unsetInfo(final String scope) {

        if(scope == null)
            _infos.clear();
        else
            _infos.remove(scope);
    }

    /**
     * Unsets all info data.
     */
    final public void unsetInfo() {
        unsetInfo(null);
    }

    /**
     * Gets the scope specific info data.
     * @param scope The scope.
     * @return The info data or null.
     */
    final  public Object getInfo(final String scope) {
        return _infos.getOrDefault(scope, null);
    }

    /**
     * Resets the scope specific measures.
     * @param scope The scope.
     */
    final public void resetMeasure(final String scope) {

        if(scope == null)
            _measures.clear();
        else
            _measures.remove(scope);
    }

    /**
     * Resets all measures.
     */
    final public void resetMeasure() {
        resetMeasure(null);
    }

    /**
     * Measures and saves the time for a specific code block.
     * @param scope The scope.
     * @param block The code block.
     */
    final public void measureTime(final String scope, final ThrowableRunnable block)
            throws Exception {

        final long t0 = System.nanoTime();
        block.apply();
        final long t1 = System.nanoTime();

        final Double elapsed = (t1 - t0) / 1000000.0;
        measureTime(scope, elapsed);
    }

    /**
     * Adds explicit the time to the specific scope.
     * @param scope The scope.
     * @param time The time.
     */
    final public void measureTime(final String scope, final Double time) {

        if(!(_measures.containsKey(scope)))
            _measures.put(scope, 0.0);

        _measures.put(scope, _measures
                .get(scope) + time);
    }

    /**
     * Gets the scope specific measure data.
     * @param scope The scope.
     * @return The measure data or -1.0.
     */
    final public Double getMeasure(final String scope) {
        return _measures.getOrDefault(scope, -1.0);
    }
}
