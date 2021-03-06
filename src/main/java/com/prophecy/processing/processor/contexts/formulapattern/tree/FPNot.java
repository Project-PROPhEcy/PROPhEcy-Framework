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
import com.prophecy.utility.node.UNode;

import java.util.*;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public final class FPNot extends UNode<FPType, IFPNode> implements IFPNode {

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
    public final List<String> getHeadAttrs() throws Exception {

        if(_headAttrs == null) {
            _headAttrs = new ArrayList<String>() {{
                addAll( getChild().getHeadAttrs() );
            }};
        }

        return _headAttrs;
    }

    /**
     * Gets the formula pattern sources.
     */
    @Override
    public final Map<Integer, FPSource> getSources() {
        return getChild().getSources();
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
                getChild().getId()
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
    public FPNot(final boolean factorize, final ICNode condition) {
        super(FPType.Not);

        _factorized = factorize;
        _condition = condition;
    }
}
