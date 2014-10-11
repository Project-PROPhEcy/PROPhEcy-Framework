/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Christian Winkel on 25.04.14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorInfo {

    //----------------------------------------
    // Annotation Variables
    //----------------------------------------


    /**
     * Saves the processor name.
     */
    public String name();


    /**
     * Saves the processor description.
     */
    public String description() default "Not available";


    /**
     * Saves the processor config.
     */
    public ProcessorConfig[] config() default { };

}
