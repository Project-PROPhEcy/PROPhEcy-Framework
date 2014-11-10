package com.prophecy.measure.types.prophecy;

import com.prophecy.measure.IMeasureInput;
import com.prophecy.processing.input.condition.*;
import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.input.term.base.ITerm;
import com.prophecy.processing.input.term.Value;
import com.prophecy.processing.processor.contexts.formulapattern.tree.*;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;

import java.util.*;

/**
 * Created by alpha_000 on 29.08.2014.
 */
public abstract class PROPhEcyMeasureInput implements IMeasureInput {

    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Returns a list of head attributes.
     * @param headAttrs The head attributes.
     * @return The head attributes list.
     */
    public static List<String> HeadAttrs(final String... headAttrs) {

        List< String > headAttrsList = new ArrayList<>();
        Collections.addAll(headAttrsList, headAttrs);
        return headAttrsList;
    }

    /**
     * Creates a formula pattern and-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The formula pattern node.
     */
    public static FPAnd FPAnd(final boolean factorize, final FPNode leftChild, final FPNode rightChild) {
        return FPAnd(factorize, new CTrue(), leftChild, rightChild);
    }

    /**
     * Creates a formula pattern and-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The formula pattern node.
     */
    public static FPAnd FPAnd(final boolean factorize, final CNode condition, final FPNode leftChild, final FPNode rightChild) {

        FPAnd fpAnd = new FPAnd(factorize, condition);

        fpAnd.setLeftChild(leftChild);
        fpAnd.setRightChild(rightChild);

        return fpAnd;
    }

    /**
     * Creates a formula pattern n-or-node with a child.
     * @param factorize Use factorization for the lineage construction.
     * @param headAttrs The projection head attributes.
     * @param child The formula pattern child.
     * @return The formula pattern n-or.
     */
    public static FPNOr FPNOr( final boolean factorize, final List<String> headAttrs, final FPNode child ) {
        return FPNOr(factorize, headAttrs, new CTrue(), child);
    }

    /**
     * Creates a formula pattern n-or-node with a child.
     * @param factorize Use factorization for the lineage construction.
     * @param headAttrs The projection head attributes.
     * @param condition The construction condition.
     * @param child The formula pattern child.
     * @return The formula pattern n-or.
     */
    public static FPNOr FPNOr( final boolean factorize, final List<String> headAttrs, final CNode condition, final FPNode child ) {

        FPNOr fpNOr = new FPNOr( factorize, headAttrs, condition );
        fpNOr.setChild( child );
        return fpNOr;
    }

    /**
     * Creates a formula pattern not-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param child The child.
     * @return The formula pattern node.
     */
    public static FPNot FPNot( final boolean factorize, final FPNode child) {
        return FPNot(factorize, new CTrue(), child);
    }

    /**
     * Creates a formula pattern not-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     * @param child The child.
     * @return The formula pattern node.
     */
    public static FPNot FPNot(final boolean factorize, final CNode condition, final FPNode child) {

        FPNot fpNot = new FPNot( factorize, condition );
        fpNot.setChild( child );
        return fpNot;
    }

    /**
     * Creates a formula pattern or-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The formula pattern node.
     */
    public static FPOr FPOr( final boolean factorize, final FPNode leftChild, final FPNode rightChild) {
        return FPOr(factorize, new CTrue(), leftChild, rightChild);
    }

    /**
     * Creates a formula pattern or-node with left and right child.
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The formula pattern node.
     */
    public static FPOr FPOr(final boolean factorize, final CNode condition, final FPNode leftChild, final FPNode rightChild) {

        FPOr fpOr = new FPOr(factorize, condition);

        fpOr.setLeftChild(leftChild);
        fpOr.setRightChild(rightChild);

        return fpOr;
    }

    /**
     * Creates a formula pattern source-node.
     * @param sourceId The specific source id.
     * @param relation The source relation.
     * @param factorize Use factorization for the lineage construction.
     * @param maskPriority The mask priority.
     * @param headAttrs The head attributes.
     * @return The formula pattern source.
     */
    public static FPSource FPSource(final int sourceId, final String relation, final boolean factorize, final int maskPriority, final List<String> headAttrs) {
        return new FPSource( sourceId, relation, factorize, maskPriority, headAttrs, new CTrue() );
    }

    /**
     * Creates a formula pattern source-node.
     * @param sourceId The specific source id.
     * @param relation The source relation.
     * @param factorize Use factorization for the lineage construction.
     * @param maskPriority The mask priority.
     * @param condition The construction condition.
     * @param headAttrs The head attributes.
     * @return The formula pattern source.
     */
    public static FPSource FPSource(final int sourceId, final String relation,final boolean factorize, final int maskPriority, final List<String> headAttrs, final CNode condition) {
        return new FPSource( sourceId, relation, factorize, maskPriority, headAttrs, condition );
    }

    /**
     * Creates a condition and-node with left and right child.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The condition node.
     */
    public static CAnd CAnd( final CNode leftChild, final CNode rightChild ) {

        CAnd cAnd = new CAnd();

        cAnd.setLeftChild( leftChild );
        cAnd.setRightChild( rightChild );

        return cAnd;
    }

    /**
     * Creates a condition false-node.
     * @return The condition node.
     */
    public static CFalse CFalse() {
        return new CFalse();
    }

    /**
     * Creates a condition not-node with child.
     * @param child The child.
     * @return The condition node.
     */
    public static CNot CNot( final CNode child ) {

        CNot cNot = new CNot();
        cNot.setChild( child );
        return cNot;
    }

    /**
     * Creates a condition op-node with left and right term.
     * @param leftTerm The left term.
     * @param rightTerm The right term.
     * @return The condition node.
     */
    public static COp COp( final ITerm leftTerm, final COpType opType, final ITerm rightTerm ) {
        return new COp(leftTerm, opType, rightTerm);
    }

    /**
     * Creates a condition or-node with left and right child.
     * @param leftChild The left child.
     * @param rightChild The right child.
     * @return The condition node.
     */
    public static COr COr( final CNode leftChild, final CNode rightChild ) {

        COr cOr = new COr();

        cOr.setLeftChild( leftChild );
        cOr.setRightChild( rightChild );

        return cOr;
    }

    /**
     * Creates a condition true-node.
     * @return The condition node.
     */
    public static CTrue CTrue() {
        return new CTrue();
    }

    /**
     * Creates a attribute with the specific name.
     * @param name The name.
     * @return The attribute.
     */
    public static Attribute Attribute( final String name ) {
        return new Attribute( name );
    }

    /**
     * Creates a value with the specific inner value.
     * @param inner The inner value.
     * @return The value.
     */
    public static Value Value( final Object inner ) {
        return new Value( inner );
    }


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the query engine.
     */
    @Override
    final public String getEngine() {
        return "PROPhEcy";
    }

    /**
     * Gets the input relation query.
     */
    public abstract String getInputRelation();

    /**
     * Gets the additional input relation queries.
     */
    public abstract Map<Integer, String> getType3InputRelations();


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Gets the formula pattern.
     * @param factorize Use factorization for the lineage construction.
     * @return The formula pattern.
     */
    public abstract FPNode getFormulaPattern(final boolean factorize);
}
