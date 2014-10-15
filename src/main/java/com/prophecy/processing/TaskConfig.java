/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing;

import com.prophecy.processing.processor.ProcessorConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 04.05.2014.
 */
public final class TaskConfig {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the available config.
     */
    private final Map<String, String> _config
            = new HashMap<>();


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * This function is used to set the default processor
     * configs if the keys doesn't exist.
     * @param processorConfigs The default processor configs.
     */
    public final void setDefaultConfig(final ProcessorConfig[] processorConfigs) {

        for(ProcessorConfig processorConfig: processorConfigs)
            _config.putIfAbsent(
                    processorConfig.key(),
                    processorConfig.value()
            );
    }


    /**
     * Determines whether the specific key is available.
     * @param key The key.
     * @return The boolean value.
     */
    public final boolean contains(final String key) {
        return _config.containsKey(key);
    }


    /**
     * Inserts the specific key-value-pair if it doesn't already exist.
     * @param key The key.
     * @param value The value.
     * @return The boolean value.
     */
    public final boolean insert( final String key, final String value) {

        if(contains(key))
            return false;

        _config.put(key, value);
        return true;
    }


    /**
     * Updates the specific key with a new value if the key exist.
     * @param key The key.
     * @param value The value.
     * @return The boolean value.
     */
    public final boolean update(final String key, final String value) {

        if(!contains(key))
            return false;

        _config.put(key, value);
        return true;
    }


    /**
     * Deletes the type specific key if the key exist.
     * @param key The key.
     * @return The boolean value.
     */
    public final boolean delete(final String key) {

        if(!contains(key))
            return false;

        _config.remove(key);
        return true;
    }


    /**
     * Requires the value for the specific key.
     * @param key The key.
     * @return The value.
     */
    @SuppressWarnings("unchecked")
    public final String require(final String key)
            throws Exception {

        if(!contains(key)) throw new Exception(
                String.format("Config[%s] not " +
                        "available.", key));

        return _config.get(key);
    }
}