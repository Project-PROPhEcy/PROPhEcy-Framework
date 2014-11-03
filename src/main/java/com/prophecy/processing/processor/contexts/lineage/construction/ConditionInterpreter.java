package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.input.condition.*;
import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.condition.base.ICNodeVisitor;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.input.term.Value;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.utility.Reference;

import java.math.BigDecimal;

/**
 * Created by alpha_000 on 03.11.2014.
 */
public class ConditionInterpreter implements ICNodeVisitor<Reference<Boolean>> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final CNode _condition;
    private final DomainTuple _domainTuple;

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param condition The condition tree.
     * @param domainTuple The domain tuple.
     */
    public ConditionInterpreter(
              final CNode condition
            , final DomainTuple domainTuple
    ) {
        _condition = condition;
        _domainTuple = domainTuple;
    }

    /**
     * Start the evaluation of teh condition tree.
     * @return The boolean value.
     */
    public final boolean evaluate() {
        final Reference<Boolean> result = new Reference<>(false);
        _condition.accept(this, result);
        return result.value;
    }

    /**
     * Visits the condition and-node.
     * @param cAnd The condition and-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CAnd cAnd, final Reference<Boolean> param) {

        final Reference<Boolean> leftResult = new Reference<>(false);
        final Reference<Boolean> rightResult = new Reference<>(false);

        cAnd.getLeftChild().accept(this, leftResult);
        cAnd.getRightChild().accept(this, rightResult);

        param.value = leftResult.value && rightResult.value;
    }

    /**
     * Visits the condition or-node.
     * @param cOr The condition or-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final COr cOr, final Reference<Boolean> param) {
        final COr or = (COr) condition;

        return evaluate(or.getLeftChild(), d)
                || evaluate(or.getRightChild(), d);
    }

    /**
     * Visits the condition false-node.
     * @param cFalse The condition false-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CFalse cFalse, final Reference<Boolean> param) {
    }

    /**
     * Visits the condition true-node.
     * @param cTrue The condition true-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CTrue cTrue, final Reference<Boolean> param) {

    }

    /**
     * Visits the condition not-node.
     * @param cNot The condition not-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CNot cNot, final Reference<Boolean> param) {
        final CNot not = (CNot) condition;

        return !evaluate(not.getChild(), d);
    }

    /**
     * Visits the condition op-node.
     * @param cOp The condition op-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final COp cOp, final Reference<Boolean> param) {
        final COp op = (COp) condition;

        Object value1;
        Object value2;

        if(op.getLTerm() instanceof Attribute)
            value1 = d.getAttr(((Attribute) op.getLTerm()).getName());
        else if(op.getLTerm() instanceof Value)
            value1 = ((Value) op.getLTerm()).getInner();
        else
            throw new Exception(
                    String.format("Unknown term type: %s",
                            op.getLTerm().getClass()));

        if(op.getRTerm() instanceof Attribute)
            value2 = d.getAttr(((Attribute) op.getRTerm()).getName());
        else if(op.getRTerm() instanceof Value)
            value2 = ((Value) op.getRTerm()).getInner();
        else
            throw new Exception(
                    String.format("Unknown term type: %s",
                            op.getRTerm().getClass()));

        if(value1 instanceof BigDecimal)
            value1 = ((BigDecimal) value1).doubleValue();
        if(value2 instanceof BigDecimal)
            value2 = ((BigDecimal) value2).doubleValue();
        if(value1 instanceof Integer)
            value1 = ((Integer) value1).doubleValue();
        if(value2 instanceof Integer)
            value2 = ((Integer) value2).doubleValue();

        switch(op.getOpType()) {

            case Equal: return value1.equals(value2);
            case Unequal: return !value1.equals(value2);
            case Greater: return ((Double)value1) > ((Double)value2);
            case Less: return ((Double)value1) < ((Double)value2);
            case GreaterEqual: return ((Double)value1) >= ((Double)value2);
            case LessEqual: return ((Double)value1) <= ((Double)value2);

            default:

                throw new Exception(
                        String.format("Unknown operation type: %s.",
                                op.getOpType()));
        }
    }
}
