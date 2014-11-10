/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.condition.base.ICNodeVisitor;
import com.prophecy.processing.input.term.base.ITerm;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class COp extends CNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final COpType _opType;
    private final ITerm _lTerm;
    private final ITerm _rTerm;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the operation type.
     */
    public final COpType getOpType() {
        return _opType;
    }

    /**
     * Gets the left term.
     */
    public final ITerm getLTerm() {
        return _lTerm;
    }

    /**
     * Gets the right term.
     */
    public final ITerm getRTerm() {
        return _rTerm;
    }

    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    @Override
    public final int getId() {
        return String.format("%s %s %s",
                getLTerm(),
                getOpType().sign(),
                getRTerm()
        ).hashCode();
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param lTerm The left term.
     * @param opType The operation type.
     * @param rTerm The right term.
     */
    public COp(final ITerm lTerm, final COpType opType, final ITerm rTerm) {
        _lTerm = lTerm;
        _opType = opType;
        _rTerm = rTerm;
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param retValue A possible return value.
     */
    @Override
    public final <ReturnT> void accept(final ICNodeVisitor<ReturnT> visitor, ReturnT retValue) {
        visitor.visit(this, retValue);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public String toTreeString() {
        return String.format("( %s %s %s )"
                , _lTerm, _opType.sign(), _rTerm);
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return _opType.sign();
    }
}
