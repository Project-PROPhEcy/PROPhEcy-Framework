package com.prophecy.measure.types.prophecy;

import com.prophecy.processing.Task;
import com.prophecy.processing.processor.IProcessorContext;
import com.prophecy.processing.processor.ProcessorInfo;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;
import com.prophecy.processing.processor.contexts.inputrelation.InputRelation;
import com.prophecy.processing.processor.contexts.inputrelation.InputRelationList;

import java.util.Map;

/**
 * Created by alpha_000 on 29.08.2014.
 */
@ProcessorInfo(name = "PROPhEcy Measure Processor", config = {})
public final class PROPhEcyMeasureContext implements IProcessorContext {

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Runs the processor context with the specific task.
     * @param task The task.
     */
    @Override
    final public void run(Task task)
            throws Exception {

        final PROPhEcyMeasureInput measureInput
                = task.getData().require(PROPhEcyMeasureInput.class);
        final boolean factorize = task.getConfig().require("Factorize").equals("1");

        final InputRelationList inputRelations = new InputRelationList();
        InputRelation inputRelation = new InputRelation(
                task.getDBAccess(), measureInput.getInputRelation());
        inputRelations.add(inputRelation);

        if( measureInput.getType3InputRelations() != null ) {
            for (final Map.Entry<Integer, String> type3InputRelationData :
                    measureInput.getType3InputRelations().entrySet()) {

                inputRelation = new InputRelation(task.getDBAccess(),
                        type3InputRelationData.getValue(),
                        type3InputRelationData.getKey()
                );
                inputRelations.add(inputRelation);
            }
        }

        task.getData().insert(
                InputRelationList.class, inputRelations);
        task.getData().insert(FPNode.class, measureInput
                .getFormulaPattern(factorize));
    }
}
