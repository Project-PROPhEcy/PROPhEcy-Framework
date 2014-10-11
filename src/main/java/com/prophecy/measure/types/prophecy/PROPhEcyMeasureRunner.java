package com.prophecy.measure.types.prophecy;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.Task;
import com.prophecy.processing.processor.ProcessorManager;
import com.prophecy.processing.processor.contexts.calculation.CalculationContext;
import com.prophecy.processing.processor.contexts.lineage.construction.LineageConstructionContext;

/**
 * Created by alpha_000 on 21.08.2014.
 */
public class PROPhEcyMeasureRunner {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private DBAccess _dbAccess = null;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param dbAccess The database access.
     */
    public PROPhEcyMeasureRunner(DBAccess dbAccess) {
        _dbAccess = dbAccess;
    }

    /**
     * Calculates the exact probability results for the specific measure input.
     * @param input The measure input.
     * @param factorize Use factorization for the lineage construction.
     * @param measureInputRelation Measure the time for the input relation.
     * @return The task info data.
     */
    public Task calculate(PROPhEcyMeasureInput input, boolean factorize, boolean measureInputRelation) {

        ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class,
                CalculationContext.class);

        Task task = new Task(null, _dbAccess);

        task.getData().insert(PROPhEcyMeasureInput.class, input);

        task.getConfig().insert("Factorize", (factorize)? "1" : "0");
        task.getConfig().insert("MeasureInputRelation", (measureInputRelation)? "1" : "0");

        manager.process(task);
        return task;
    }

    /**
     * Calculates the approximate probability results for the specific measure input.
     * @param input The measure input.
     * @param errorConstant The error constant.
     * @param isRelativeError Determines whether the error is relative or absolute.
     * @param factorize Use factorization for the lineage construction.
     * @param measureInputRelation Measure the time for the input relation.
     * @return The task info data.
     */
    public Task approximate(PROPhEcyMeasureInput input, double errorConstant, boolean isRelativeError, boolean factorize, boolean measureInputRelation) {

        ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class,
                CalculationContext.class);

        Task task = new Task(null, _dbAccess);

        task.getData().insert(PROPhEcyMeasureInput.class, input);

        task.getConfig().insert("Factorize", (factorize)? "1" : "0");
        task.getConfig().insert("MeasureInputRelation", (measureInputRelation)? "1" : "0");
        task.getConfig().insert("IsRelativeError", (isRelativeError)? "1" : "0");
        task.getConfig().insert("ErrorConstant", String.valueOf(errorConstant));
        task.getConfig().insert("ApproximationEnabled", "1");

        manager.process(task);
        return task;
    }

    /**
     * Calculates the exact probability results for the specific measure input.
     * @param input The measure input.
     * @param factorize Use factorization for the lineage construction.
     * @param measureInputRelation Measure the time for the input relation.
     * @return The task info data.
     */
    public Task constructLineage(PROPhEcyMeasureInput input, boolean factorize, boolean measureInputRelation) {

        ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class);

        Task task = new Task(null, _dbAccess);

        task.getData().insert(PROPhEcyMeasureInput.class, input);

        task.getConfig().insert("factorize", (factorize)? "1" : "0");
        task.getConfig().insert("measureInputRelation", (measureInputRelation)? "1" : "0");

        manager.process(task);
        return task;
    }
}