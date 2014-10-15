package com.prophecy.processing.processor.contexts.calculation;

/**
 * Created by alpha_000 on 05.08.2014.
 */
public final class Probability {

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
    public final Double getExact() {
        return _exact;
    }

    /**
     * Sets the exact probability.
     */
    public final void setExact(final Double value) {
        _exact = value;
    }

    /**
     * Gets the upper bound probability.
     */
    public final Double getUB() {
        return _ub;
    }

    /**
     * Sets the upper bound probability.
     */
    public final void setUB(final Double value) {
        _ub = value;
    }

    /**
     * Gets the lower bound probability.
     */
    public final Double getLB() {
        return _lb;
    }

    /**
     * Sets the lower bound probability.
     */
    public final void setLB(final Double value) {
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
    public Probability(final Double defaultValue) {
        this(defaultValue, defaultValue, defaultValue);
    }

    /**
     * Constructor
     * @param exact The exact probability.
     * @param ub The upper bound probability.
     * @param lb The lower bound probability.
     */
    public Probability(final Double exact, final Double ub, final Double lb) {

        _exact = exact;
        _ub = ub;
        _lb = lb;
    }

    /**
     * Multiplies the exact probability with the specific factor.
     * @param factor The factor.
     * @return The new exact probability.
     */
    public final Double mulExact(final Double factor) {
        return _exact *= factor;
    }

    /**
     * Multiplies the upper bound probability with the specific factor.
     * @param factor The factor.
     * @return The new upper bound probability.
     */
    public final Double mulUB(final Double factor) {
        return _ub *= factor;
    }

    /**
     * Multiplies the lower bound probability with the specific factor.
     * @param factor The factor.
     * @return The new lower bound probability.
     */
    public final Double mulLB(final Double factor) {
        return _lb *= factor;
    }

    /**
     * Adds up the exact probability with the specific addend.
     * @param addend The addend.
     * @return The new exact probability.
     */
    public final Double addExact(final Double addend) {
        return _exact += addend;
    }

    /**
     * Adds up the upper bound probability with the specific factor.
     * @param addend The addend.
     * @return The new upper bound probability.
     */
    public final Double addUB(final Double addend) {
        return _ub += addend;
    }

    /**
     * Adds up the lower bound probability with the specific factor.
     * @param addend The addend.
     * @return The new lower bound probability.
     */
    public final Double addLB(final Double addend) {
        return _lb += addend;
    }

    /**
     * Sets the probability for all Properties.
     * @param value The probability value.
     */
    public final void setAll(final Double value) {

        _exact = value;
        _ub = value;
        _lb = value;
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    public final String toString() {
        return String.format(
                "[ P: %s | LB: %s | UB: %s ]", _exact, _lb, _ub);
    }
}
