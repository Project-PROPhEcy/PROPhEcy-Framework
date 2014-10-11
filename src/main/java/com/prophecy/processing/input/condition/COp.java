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
public class COp extends Node<CType> implements ICNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the operation type.
     */
    private COpType _opType = null;


    /**
     * Saves the left term.
     */
    private ITerm _lTerm = null;


    /**
     * Saves teh right term.
     */
    private ITerm _rTerm = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the operation type.
     */
    public COpType getOpType() {
        return _opType;
    }

    /**
     * Gets the left term.
     */
    public ITerm getLTerm() {
        return _lTerm;
    }

    /**
     * Gets the right term.
     */
    public ITerm getRTerm() {
        return _rTerm;
    }

    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    @Override
    public int getId() {
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
     * @param rTerm The rigth term.
     * @param opType The operation type.
     */
    public COp(ITerm lTerm, COpType opType, ITerm rTerm) {
        super(CType.Op);

        _opType = opType;

        _lTerm = lTerm;
        _rTerm = rTerm;

    }
}
