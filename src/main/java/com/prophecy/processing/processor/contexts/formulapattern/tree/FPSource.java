/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.formulapattern.tree;

import com.prophecy.processing.input.condition.CTrue;
import com.prophecy.processing.input.condition.ICNode;
import com.prophecy.utility.node.Node;

import java.util.*;

/**
 * Created by alpha_000 on 27.05.2014.
 */
// TODO Typ3 Source
public final class FPSource extends Node<FPType> implements IFPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the source id for the
     * later vertical construction.
     */
    private int _sourceId = -1;

    /**
     * Saves the source relation.
     */
    private String _relation = null;

    /**
     * Saves the factorized state.
     */
    private boolean _factorized = false;

    /**
     * Saves the mask priority.
     */
    private int _maskPriority = 0;

    /**
     * Saves the head attributes.
     */
    private List<String> _headAttrs = null;

    /**
     * Saves the condition.
     */
    private ICNode _condition = new CTrue();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the source id for the
     * later vertical construction.
     */
    public int getSourceId() {
        return _sourceId;
    }


    /**
     * Gets the source relation.
     */
    public String getRelation() {
        return _relation;
    }


    /**
     * Gets the formula pattern head attributes.
     */
    @Override
    public List<String> getHeadAttrs() throws Exception {
        return _headAttrs;
    }

    /**
     * Gets the formula pattern sources.
     */
    @Override
    public Map<Integer, FPSource> getSources() {

        Map<Integer, FPSource> sources
                = new HashMap<>();
        sources.put(_sourceId, this);
        return sources;
    }

    /**
     * Gets the construction condition.
     */
    @Override
    public ICNode getCondition() {
        return _condition;
    }

    /**
     * Determines whether the lineage
     * nodes should be factorized.
     */
    @Override
    public boolean isFactorized() {
        return _factorized;
    }

    /**
     * Gets the mask priority.
     */
    public int getMaskPriority() {
        return _maskPriority;
    }

    /**
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    @Override
    public int getId() {
        return Arrays.hashCode(new int[]{
                getSourceId(),
                getCondition().getId(),
        });
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param sourceId The specific source id.
     * @param relation The source relation.
     * @param factorize Use factorization for the lineage construction.
     * @param maskPriority The mask priority.
     * @param headAttrs The head attributes.
     * @param condition The construction Condition.
     */
    public FPSource(int sourceId, String relation, boolean factorize, int maskPriority, List<String> headAttrs, ICNode condition) {
        super(FPType.Source);

        _sourceId = sourceId;
        _relation = relation;
        _factorized = factorize;
        _maskPriority = maskPriority;
        _condition = condition;
        _headAttrs = headAttrs;
    }
}
