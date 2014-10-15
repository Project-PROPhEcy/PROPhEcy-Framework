/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor;

import com.prophecy.processing.Task;

/**
 * Created by Christian Winkel on 15.04.14.
 */
public final class Processor<T extends IProcessorContext> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the processor context.
     */
    private final Class<T> _context;


    /**
     * Saves the processor info.
     */
    private final ProcessorInfo _info;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the processor name.
     */
    public final String getName() {
        return _info.name();
    }


    /**
     * Gets the processor description.
     */
    public final String getDescription() {
        return _info.description();
    }


    /**
     * Gets the default processor config.
     */
    public final ProcessorConfig[] getDefaultProcessorConfig() {
        return _info.config();
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param context The processor context.
     */
    public Processor(final Class<T> context) {

        _context = context;
        _info = context.getAnnotation(
                ProcessorInfo.class);
    }


    /**
     * Processes the specific task.
     * @param task The task.
     */
    public final void process(final Task task)
            throws Exception{

        if(_info == null) {
            throw new Exception("There is no Processor " +
                    "Info defined for the Context: " +
                            _context.getName() + ".");
        }

        task.getConfig().setDefaultConfig(
                _info.config());

        final T context = _context.newInstance();
        context.run(task);
    }
}
