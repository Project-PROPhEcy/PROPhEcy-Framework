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
public class TaskInfo {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the time measures for various scopes.
     */
    private Map<String, Double> _measures = new HashMap<>();


    /**
     * Saves the infos for various scopes.
     */
    private Map<String, Object> _infos = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the available time scopes.
     */
    public Set<String> getMeasureScopes() {
        return _measures.keySet();
    }


    /**
     * Gets the available value scopes.
     */
    public Set<String> getInfoScopes() {
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
    public void setInfo(String scope, Object info) {
        _infos.put(scope, info);
    }

    /**
     * Unsets the info data for the specific scope.
     * @param scope The scope.
     */
    public void unsetInfo(String scope) {

        if(scope == null)
            _infos.clear();
        else
            _infos.remove(scope);
    }

    /**
     * Unsets all info data.
     */
    public void unsetInfo() {
        unsetInfo(null);
    }

    /**
     * Gets the scope specific info data.
     * @param scope The scope.
     * @return The info data or null.
     */
    public Object getInfo(String scope) {
        return _infos.getOrDefault(scope, null);
    }

    /**
     * Resets the scope specific measures.
     * @param scope The scope.
     */
    public void resetMeasure(String scope) {

        if(scope == null)
            _measures.clear();
        else
            _measures.remove(scope);
    }

    /**
     * Resets all measures.
     */
    public void resetMeasure() {
        resetMeasure(null);
    }

    /**
     * Measures and saves the time for a specific code block.
     * @param scope The scope.
     * @param block The code block.
     */
    public void measureTime(String scope, ThrowableRunnable block)
            throws Exception {

        long t0 = System.nanoTime();
        block.apply();
        long t1 = System.nanoTime();

        Double elapsed = (t1 - t0) / 1000000.0;
        measureTime(scope, elapsed);
    }

    /**
     * Adds explicit the time to the specific scope.
     * @param scope The scope.
     * @param time The time.
     */
    public void measureTime(String scope, Double time) {

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
    public Double getMeasure(String scope) {
        return _measures.getOrDefault(scope, -1.0);
    }
}
