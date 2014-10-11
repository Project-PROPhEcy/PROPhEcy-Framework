/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation;

import com.prophecy.processing.output.TupleResults;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.construction.FactorCatalog;

/**
 * Created by alpha_000 on 18.07.2014.
 */
public interface ICalculation {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the used root factor catalog.
     */
    public FactorCatalog getFactorCatalog();

    /**
     * Gets the used event manager.
     */
    public EventManager getEventManager();

    /**
     * Gets the tuple results.
     */
    public TupleResults getTupleResults();

    /**
     * Gets the number of events in lineage formula.
     */
    public int getEventCount();

    /**
     * Gets the number of variables in the lineage formula.
     */
    public int getVariablesCount();

    /**
     * Gets the number of used masks to calculate the lineage formula.
     */
    public int getMaskCount();


    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * Initializes the probability calculatons.
     */
    public void initialize() throws Exception;


    /**
     * Executes the probability calculations.
     */
    public void execute() throws Exception;
}
