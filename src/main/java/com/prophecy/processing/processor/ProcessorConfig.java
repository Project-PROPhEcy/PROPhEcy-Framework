package com.prophecy.processing.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Christian Winkel on 25.04.14.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ProcessorConfig {

    //----------------------------------------
    // Annotation Variables
    //----------------------------------------


    /**
     * Saves the config key.
     */
    public String key();


    /**
     * Saves the config value.
     */
    public String value();

}
