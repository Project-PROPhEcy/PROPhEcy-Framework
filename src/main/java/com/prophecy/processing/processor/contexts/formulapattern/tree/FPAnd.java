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
import com.prophecy.utility.ListUtils;
import com.prophecy.utility.node.BNode;

import java.util.*;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public final class FPAnd extends BNode<FPType, IFPNode> implements IFPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the factorized state.
     */
    private final boolean _factorized;

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
     * Gets the formula pattern head attributes.
     * @return The head attributes.
     */
    @Override
    public final List<String> getHeadAttrs()
            throws Exception {

        if(_headAttrs == null) {

            _headAttrs = new ArrayList<>();

            final List<String> lHeadAttrs = getLeftChild().getHeadAttrs();
            final List<String> rHeadAttrs = getRightChild().getHeadAttrs();

            if(getRightChild()
                    .getType() == FPType.Not) {

                if(!lHeadAttrs.containsAll(rHeadAttrs)
                        || !rHeadAttrs.containsAll(lHeadAttrs))
                    throw new Exception("Head Attributes " +
                            "from left and right child need to be the same.");

                _headAttrs.addAll(lHeadAttrs);
            }
            else {

                _headAttrs.addAll(lHeadAttrs);
                _headAttrs.addAll(rHeadAttrs);

                return ListUtils
                        .RemoveDuplicates(_headAttrs);
            }
        }

        return _headAttrs;
    }

    /**
     * Gets the formula pattern sources.
     */
    @Override
    public final Map<Integer, FPSource> getSources() {

        final Map<Integer, FPSource> sources
                = getLeftChild().getSources();
        sources.putAll(getRightChild().getSources());

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
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    @Override
    public final int getId() {

        return Arrays.hashCode(new int[]{
                getType().hashCode(),
                getCondition().getId(),
                getLeftChild().getId(),
                getRightChild().getId()
        });
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction Condition.
     */
    public FPAnd(final boolean factorize, final ICNode condition) {
        super(FPType.And);

        _factorized = factorize;
        _condition = condition;
    }
}
