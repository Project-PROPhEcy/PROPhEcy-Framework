/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class TaskData {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the available data.
     */
    private Map<Class<?>, Object> _data
            = new HashMap<>();


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Determines whether the type specific key is available.
     * @param key The key.
     * @return The boolean value.
     */
    public <T> boolean contains(Class<T> key) {
        return _data.containsKey(key);
    }


    /**
     * Inserts the type specific key-value-pair if it doesn't already exist.
     * @param key The key.
     * @param value The value.
     * @return The boolean value.
     */
    public <T> boolean insert(Class<T> key, T value) {

        if(contains(key))
            return false;

        _data.put(key, value);
        return true;
    }


    /**
     * Updates the type specific key with a new value if the key exist.
     * @param key The key.
     * @param value The value.
     * @return The boolean value.
     */
    public <T> boolean update(Class<T> key, T value) {

        if(!contains(key))
            return false;

        _data.put(key, value);
        return true;
    }


    /**
     * Deletes the type specific key if the key exist.
     * @param key The key.
     * @return The boolean value.
     */
    public <T> boolean delete(Class<T> key) {

        if(!contains(key))
            return false;

        _data.remove(key);
        return true;
    }


    /**
     * Requires the value for the type specific key.
     * @param key The key.
     * @return The value.
     */
    @SuppressWarnings("unchecked")
    public <T> T require(Class<T> key)
            throws Exception {

        if(!contains(key)) throw new Exception(
                String.format("Data[%s] not " +
                        "available in Task.", key));

        return (T) _data.get(key);
    }

}