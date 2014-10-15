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
    private final int _sourceId;

    /**
     * Saves the source relation.
     */
    private final String _relation;

    /**
     * Saves the factorized state.
     */
    private final boolean _factorized;

    /**
     * Saves the mask priority.
     */
    private final int _maskPriority;

    /**
     * Saves the head attributes.
     */
    private List<String> _headAttrs = null;

    /**
     * Saves the condition.
     */
    private final ICNode _condition;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the source id for the
     * later vertical construction.
     */
    public final int getSourceId() {
        return _sourceId;
    }


    /**
     * Gets the source relation.
     */
    public final String getRelation() {
        return _relation;
    }


    /**
     * Gets the formula pattern head attributes.
     */
    @Override
    public final List<String> getHeadAttrs() throws Exception {
        return _headAttrs;
    }

    /**
     * Gets the formula pattern sources.
     */
    @Override
    public final Map<Integer, FPSource> getSources() {

        Map<Integer, FPSource> sources
                = new HashMap<>();
        sources.put(_sourceId, this);
        return sources;
    }

    /**
     * Gets the construction condition.
     */
    @Override
    public final ICNode getCondition() {
        return _condition;
    }

    /**
     * Determines whether the lineage
     * nodes should be factorized.
     */
    @Override
    public final boolean isFactorized() {
        return _factorized;
    }

    /**
     * Gets the mask priority.
     */
    public final int getMaskPriority() {
        return _maskPriority;
    }

    /**
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    @Override
    public final int getId() {
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
    public FPSource(final int sourceId, final String relation,
                    final boolean factorize, final int maskPriority,
                    final List<String> headAttrs, final ICNode condition) {
        super(FPType.Source);

        _sourceId = sourceId;
        _relation = relation;
        _factorized = factorize;
        _maskPriority = maskPriority;
        _condition = condition;
        _headAttrs = headAttrs;
    }
}
