/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor;

import com.prophecy.processing.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian Winkel on 23.04.14.
 */
public class ProcessorManager {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the available processors.
     */
    private List<Processor<?>> _processors
            = new ArrayList<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param contexts The processor contexts which should be chained.
     */
    @SafeVarargs
    public <T extends IProcessorContext> ProcessorManager(Class<? extends T> ...contexts){

        for (Class<? extends T> context: contexts)
            _processors.add(new Processor<>(context));
    }

    /**
     * Processes the specific task.
     * @param task The task.
     */
    public void process(Task task) {

        try {
            for(Processor<?> processor: _processors) {
                processor.process(task);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}