/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

import com.prophecy.processing.input.term.ITerm;
import com.prophecy.utility.node.Node;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class COp extends Node<CType> implements ICNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the operation type.
     */
    private final COpType _opType;


    /**
     * Saves the left term.
     */
    private final ITerm _lTerm;


    /**
     * Saves the right term.
     */
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
                getOpType().getSign(),
                getRTerm()
        ).hashCode();
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param lTerm The left term.
     * @param rTerm The right term.
     * @param opType The operation type.
     */
    public COp(final ITerm lTerm, final COpType opType, final ITerm rTerm) {
        super(CType.Op);

        _opType = opType;

        _lTerm = lTerm;
        _rTerm = rTerm;

    }
}
