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
import com.prophecy.utility.node.BNode;

import java.util.*;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public final class FPOr extends BNode<FPType, IFPNode> implements IFPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the factorized state.
     */
    private boolean _factorized = false;

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
     * Gets the formula pattern head attributes.
     * @return The head attributes.
     */
    @Override
    public List<String> getHeadAttrs() throws Exception {

        if(_headAttrs == null) {

            _headAttrs = new ArrayList<>();

            List<String> lHeadAttrs = getLeftChild().getHeadAttrs();
            List<String> rHeadAttrs = getRightChild().getHeadAttrs();

            if( ! lHeadAttrs.containsAll(rHeadAttrs)
                    || ! rHeadAttrs.containsAll(lHeadAttrs))
                throw new Exception("Head Attributes " +
                        "from left and right child need to be the same.");

            _headAttrs.addAll(lHeadAttrs);
        }

        return _headAttrs;
    }

    /**
     * Gets the formula pattern source ids.
     */
    @Override
    public Map<Integer, FPSource> getSources() {

        Map<Integer, FPSource> sources
                = getLeftChild().getSources();
        sources.putAll(getRightChild().getSources());

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
        return false;
    }

    /**
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    @Override
    public int getId() {

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
    public FPOr(boolean factorize, ICNode condition) {
        super(FPType.Or);

        _factorized = factorize;
        _condition = condition;
    }
}
