package com.prophecy.measure.types.prophecy;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.Task;
import com.prophecy.processing.processor.ProcessorManager;
import com.prophecy.processing.processor.contexts.calculation.CalculationContext;
import com.prophecy.processing.processor.contexts.lineage.construction.LineageConstructionContext;

/**
 * Created by alpha_000 on 21.08.2014.
 */
public final class PROPhEcyMeasureRunner {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    final private DBAccess _dbAccess;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param dbAccess The database access.
     */
    public PROPhEcyMeasureRunner(final DBAccess dbAccess) {
        _dbAccess = dbAccess;
    }

    /**
     * Calculates the exact probability results for the specific measure input.
     * @param input The measure input.
     * @param factorize Use factorization for the lineage construction.
     * @param measureInputRelation Measure the time for the input relation.
     * @return The task info data.
     */
    final public Task calculate(final PROPhEcyMeasureInput input, final boolean factorize, final boolean measureInputRelation) {

        final ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class,
                CalculationContext.class);

        final Task task = new Task(null, _dbAccess);

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
    final public Task approximate(final PROPhEcyMeasureInput input, final double errorConstant, final boolean isRelativeError, final boolean factorize, final boolean measureInputRelation) {

        final ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class,
                CalculationContext.class);

        final Task task = new Task(null, _dbAccess);

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
    final public Task constructLineage(final PROPhEcyMeasureInput input, final boolean factorize, final boolean measureInputRelation) {

        final ProcessorManager manager = new ProcessorManager(
                PROPhEcyMeasureContext.class,
                LineageConstructionContext.class);

        final Task task = new Task(null, _dbAccess);

        task.getData().insert(PROPhEcyMeasureInput.class, input);

        task.getConfig().insert("factorize", (factorize)? "1" : "0");
        task.getConfig().insert("measureInputRelation", (measureInputRelation)? "1" : "0");

        manager.process(task);
        return task;
    }
}