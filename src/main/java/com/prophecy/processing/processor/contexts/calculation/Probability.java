package com.prophecy.processing.processor.contexts.calculation;

/**
 * Created by alpha_000 on 05.08.2014.
 */
public class Probability {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private Double _exact = 0.0;
    private Double _ub = 0.0;
    private Double _lb = 0.0;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the exact probability.
     */
    public Double getExact() {
        return _exact;
    }

    /**
     * Sets the exact probability.
     */
    public void setExact(Double value) {
        _exact = value;
    }

    /**
     * Gets the upper bound probability.
     */
    public Double getUB() {
        return _ub;
    }

    /**
     * Sets the upper bound probability.
     */
    public void setUB(Double value) {
        _ub = value;
    }

    /**
     * Gets the lower bound probability.
     */
    public Double getLB() {
        return _lb;
    }

    /**
     * Sets the lower bound probability.
     */
    public void setLB(Double value) {
        _lb = value;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     */
    public Probability() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Constructor
     * @param defaultValue The default value.
     */
    public Probability(Double defaultValue) {
        this(defaultValue, defaultValue, defaultValue);
    }

    /**
     * Constructor
     * @param exact The exact probability.
     * @param ub The upper bound probability.
     * @param lb The lower bound probability.
     */
    public Probability(Double exact, Double ub, Double lb) {

        _exact = exact;
        _ub = ub;
        _lb = lb;
    }

    /**
     * Multiplies the exact probability with the specific factor.
     * @param factor The factor.
     * @return The new exact probability.
     */
    public Double mulExact(Double factor) {
        return _exact *= factor;
    }

    /**
     * Multiplies the upper bound probability with the specific factor.
     * @param factor The factor.
     * @return The new upper bound probability.
     */
    public Double mulUB(Double factor) {
        return _ub *= factor;
    }

    /**
     * Multiplies the lower bound probability with the specific factor.
     * @param factor The factor.
     * @return The new lower bound probability.
     */
    public Double mulLB(Double factor) {
        return _lb *= factor;
    }

    /**
     * Adds up the exact probability with the specific addend.
     * @param addend The addend.
     * @return The new exact probability.
     */
    public Double addExact(Double addend) {
        return _exact += addend;
    }

    /**
     * Adds up the upper bound probability with the specific factor.
     * @param addend The addend.
     * @return The new upper bound probability.
     */
    public Double addUB(Double addend) {
        return _ub += addend;
    }

    /**
     * Adds up the lower bound probability with the specific factor.
     * @param addend The addend.
     * @return The new lower bound probability.
     */
    public Double addLB(Double addend) {
        return _lb += addend;
    }

    /**
     * Sets the probability for all Properties.
     * @param value The probability value.
     */
    public void setAll(Double value) {

        _exact = value;
        _ub = value;
        _lb = value;
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    public String toString() {
        return String.format(
                "[ P: %s | LB: %s | UB: %s ]", _exact, _lb, _ub);
    }
}
