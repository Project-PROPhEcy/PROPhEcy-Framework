/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.calculation;

import com.prophecy.processing.Task;
import com.prophecy.processing.output.TupleResults;
import com.prophecy.processing.processor.IProcessorContext;
import com.prophecy.processing.processor.ProcessorConfig;
import com.prophecy.processing.processor.ProcessorInfo;
import com.prophecy.processing.processor.contexts.calculation.approx.ApproxCalculation;
import com.prophecy.processing.processor.contexts.calculation.exact.ExactCalculation;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.construction.FactorCatalog;

/**
 * Created by alpha_000 on 09.07.2014.
 */
@ProcessorInfo(name = "Calculation Processor", config = {
        @ProcessorConfig(key = "CalculationEnabled", value = "1"),
        @ProcessorConfig(key = "ApproximationEnabled", value = "0"),
        @ProcessorConfig(key = "IsRelativeError", value = "1"),
        @ProcessorConfig(key = "ErrorConstant", value = "0.0")
})
public final class CalculationContext implements IProcessorContext {

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Runs the processor context with the specific task.
     * @param task The task.
     */
    @Override
    public final void run(final Task task)
            throws Exception {

        final FactorCatalog factorCatalog = task.getData()
                .require(FactorCatalog.class);
        final EventManager eventManager = task.getData()
                .require(EventManager.class);

        // TODO gewisse Einstellungen mit Bitflags lösen
        final String errorConstant = task.getConfig().require("ErrorConstant");
        final String isRelativeError = task.getConfig().require("IsRelativeError");
        final String calculationEnabled = task.getConfig().require("CalculationEnabled");
        final String approximationEnabled = task.getConfig().require("ApproximationEnabled");

        ICalculation calculation;

        if(approximationEnabled.equals("1")) {
            calculation = new ApproxCalculation(
                    factorCatalog, eventManager,
                    Double.parseDouble(errorConstant),
                    isRelativeError.equals("1"));
        }
        else {
            calculation = new ExactCalculation(
                    factorCatalog, eventManager);
        }

        task.getInfo().measureTime(
                "Calculation", calculation::initialize);

        task.getInfo().setInfo("Events",
                calculation.getEventCount());
        task.getInfo().setInfo("Variables",
                calculation.getVariablesCount());

        // TODO: Man könnte für Boolean etc static Literale erzeugen.
        if(calculationEnabled.equals("1")) {
            task.getInfo().measureTime(
                    "Calculation", calculation::execute);

            task.getInfo().setInfo("Masks",
                    calculation.getMaskCount());

            task.getData().insert(TupleResults.class,
                    calculation.getTupleResults());
        }
    }
}
