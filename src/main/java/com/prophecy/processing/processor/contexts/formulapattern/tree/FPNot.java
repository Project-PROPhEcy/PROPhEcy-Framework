/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.formulapattern.tree;

import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPUNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.IFPNodeVisitor;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.LUNot;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

import java.util.*;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public final class FPNot extends FPUNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private List<String> _headAttrs = null;

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
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    @Override
    public final int getId() {
        return Arrays.hashCode(new int[]{
                FPNot.class.hashCode(),
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
    public FPNot(final boolean factorize, final CNode condition) {
        super(factorize, condition);
    }

    /**
     * Creates a lineage node from the formula pattern node.
     * @return The lineage node.
     */
    @Override
    public LNode createLineageNode() {
        return new LUNot();
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId current The source id.
     */
    @Override
    public void accept(IFPNodeVisitor visitor, LNode lNode, DomainTuple d, int sourceId) {
        visitor.visit(this, lNode, d, sourceId);
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "Not";
    }

}
