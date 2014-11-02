/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.formulapattern.tree;

import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.IFPNodeVisitor;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.processing.processor.contexts.lineage.tree.LSource;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alpha_000 on 27.05.2014.
 */
// TODO Typ3 Source
public final class FPSource extends FPNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final int _sourceId;
    private final int _maskPriority;

    private final String _relation;

    private List<String> _headAttrs = null;

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
                    final List<String> headAttrs, final CNode condition) {
        super(factorize, condition);

        _sourceId = sourceId;
        _relation = relation;
        _maskPriority = maskPriority;
        _headAttrs = headAttrs;
    }

    /**
     * Creates a lineage node from the formula pattern node.
     * @return The lineage node.
     */
    @Override
    public LNode createLineageNode() {

        LSource lSource = new LSource();
        lSource.setMaskPriority(
                getMaskPriority());

        return lSource;
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
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    public String toTreeString() {
        return String.format("%s[%s|%s|(%s)]"
                , toString(), getSourceId(), getRelation()
                , _headAttrs.stream().collect(Collectors.joining(", ")));
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "Source";
    }

}
