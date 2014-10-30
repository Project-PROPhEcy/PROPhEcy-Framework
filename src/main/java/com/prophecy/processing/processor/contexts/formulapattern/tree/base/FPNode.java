/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.formulapattern.tree.base;

import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.FPSource;

import java.util.List;
import java.util.Map;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public abstract class FPNode implements IFPNodeVisitable {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final boolean _factorized;
    private final CNode _condition;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the formula pattern head attributes.
     */
    public abstract List<String> getHeadAttrs()
            throws Exception;

    /**
     * Gets the formula pattern sources.
     */
    public abstract Map<Integer, FPSource> getSources();

    /**
     * Gets the construction condition.
     */
    public CNode getCondition() {
        return _condition;
    }

    /**
     * Determines whether the lineage
     * nodes should be factorized.
     */
    public boolean isFactorized() {
        return _factorized;
    }

    /**
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    public abstract int getId();

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param factorize Use factorization for the lineage construction.
     * @param condition The construction condition.
     */
    public FPNode(final boolean factorize, final CNode condition) {
        _factorized = factorize;
        _condition = condition;
    }

    /**
     * Determines whether this formula pattern
     * contains the specific source id.
     * @param sourceId The source id.
     * @return The boolean value.
     */
    public boolean containsSourceId(int sourceId) {
        return getSources().keySet().contains(sourceId);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    public abstract String toTreeString();
}
