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
 * Created by Christian Winkel on 25.04.14.
 */
public interface IProcessorContext {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Runs the processor context with the specific task.
     * @param task The task.
     */
    public void run(final Task task)
            throws Exception;
}
