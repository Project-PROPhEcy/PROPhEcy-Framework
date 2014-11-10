package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.input.condition.*;
import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.condition.base.ICNodeVisitor;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.input.term.Value;
import com.prophecy.processing.input.term.base.ITermVisitor;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.utility.Reference;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by alpha_000 on 03.11.2014.
 */
public class ConditionInterpreter implements ICNodeVisitor<Reference<Boolean>>, ITermVisitor<Void> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final CNode _condition;
    private final DomainTuple _domainTuple;

    private Object _value1 = null;
    private Object _value2 = null;

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

        final Reference<Boolean> leftResult = new Reference<>(false);
        final Reference<Boolean> rightResult = new Reference<>(false);

        cOr.getLeftChild().accept(this, leftResult);
        cOr.getRightChild().accept(this, rightResult);

        param.value = leftResult.value || rightResult.value;
    }

    /**
     * Visits the condition false-node.
     * @param cFalse The condition false-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CFalse cFalse, final Reference<Boolean> param) {
        param.value = false;
    }

    /**
     * Visits the condition true-node.
     * @param cTrue The condition true-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CTrue cTrue, final Reference<Boolean> param) {
        param.value = true;
    }

    /**
     * Visits the condition not-node.
     * @param cNot The condition not-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final CNot cNot, final Reference<Boolean> param) {

        final Reference<Boolean> result
                = new Reference<>(false);

        cNot.getChild().accept(this, result);
        param.value = ! result.value;
    }

    /**
     * Visits the condition op-node.
     * @param cOp The condition op-node.
     * @param param A possible parameter.
     */
    @Override
    public final void visit(final COp cOp, final Reference<Boolean> param) {

        cOp.getLTerm().accept(this, null);
        cOp.getRTerm().accept(this, null);

        if(_value1 == null || _value2 == null) {
            param.value = false;
            return;
        }

        if(_value1 instanceof BigDecimal)
            _value1 = ((BigDecimal) _value1).doubleValue();
        if(_value2 instanceof BigDecimal)
            _value2 = ((BigDecimal) _value2).doubleValue();
        if(_value1 instanceof Integer)
            _value1 = ((Integer) _value1).doubleValue();
        if(_value2 instanceof Integer)
            _value2 = ((Integer) _value2).doubleValue();

        switch(cOp.getOpType()) {

            case Equal: param.value = _value1.equals(_value2);
            case Unequal: param.value = ! _value1.equals(_value2);
            case Greater: param.value = ((Double)_value1) > ((Double)_value2);
            case Less: param.value = ((Double)_value1) < ((Double)_value2);
            case GreaterEqual: param.value = ((Double)_value1) >= ((Double)_value2);
            case LessEqual: param.value = ((Double)_value1) <= ((Double)_value2);

            default: param.value = false;
        }

        _value1 = null;
        _value2 = null;
    }

    /**
     * Visits the attribute.
     * @param attribute The attribute.
     * @param param A possible parameter.
     */
    @Override
    public void visit(Attribute attribute, Void param) {
        try {
            if(_value1 == null)
                _value1 = _domainTuple.getAttr(attribute.getName());
            else
                _value2 = _domainTuple.getAttr(attribute.getName());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Visits the value.
     * @param value The value.
     * @param param A possible parameter.
     */
    @Override
    public void visit(Value value, Void param) {
        if(_value1 == null)
            _value1 = value.getInner();
        else
            _value2 = value.getInner();
    }
}
