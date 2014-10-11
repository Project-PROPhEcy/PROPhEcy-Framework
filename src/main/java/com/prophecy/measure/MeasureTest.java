package com.prophecy.measure;

import com.prophecy.processing.TaskInfo;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * Created by alpha_000 on 15.08.2014.
 */
public abstract class MeasureTest {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves a unique test id.
     */
    final private String _id = UUID.randomUUID().toString();


    /**
     * Saves the measure input.
     */
    final private IMeasureInput _input;


    /**
     * Saves the number of iteration.
     */
    final private int _iterate;


    /**
     * Saves the duration before an timeout occurs in minuets.
     */
    final private long _timeout;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the unique test id.
     */
    final public String getId() {
        return _id;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param input The input for this measure test.
     * @param iterate The number of iterations.
     * @param timeout The duration before an timeout occurs in minuets.
     */
    public MeasureTest(final IMeasureInput input, final int iterate, final long timeout) {

        _input = input;
        _iterate = iterate;
        _timeout = timeout;
    }


    /**
     * Runs the measure test.
     * @param suite The responsible measure suite.
     */
    final public void run(final MeasureSuit suite) {

        System.out.println("---------------------------------------------");
        System.out.println(String.format(
                "Start Measure Test[N: %s | T: %s | I: %s]",
                _input.getName(), _input.getTyping(), _iterate));

        for(int i = 1; i <= _iterate; i++) {

            final ExecutorService executor
                    = Executors.newSingleThreadExecutor();

            final Future future = executor
                    .submit(this::process);

            // This does not cancel
            // the already-scheduled task.
            executor.shutdown();

            try {

                final TaskInfo info = (TaskInfo)future
                        .get(_timeout, TimeUnit.MINUTES);

                System.out.println(String.format(
                        "Iteration[%s / %s] completed", i, _iterate));
            }
            catch(TimeoutException e) {
                // TODO Timeout behandeln.
            }
            catch(Exception e) {
                // TODO Exception behandeln.
            }
        }
    }


    /**
     * Processes the measure test.
     * @return The collected infos from the task.
     */
    protected abstract TaskInfo process();
}