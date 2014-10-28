/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public enum COpType {

    //----------------------------------------
    // Enumeration Values
    //----------------------------------------

    GreaterEqual(">="),
    LessEqual("<="),
    Unequal("!="),
    Greater(">"),
    Equal("="),
    Less("<");

    //----------------------------------------
    // Enumeration Variables
    //----------------------------------------

    /**
     * Saves the operation sign.
     */
    private String _sign = null;

    //----------------------------------------
    // Enumeration Properties
    //----------------------------------------

    /**
     * Gets the operation sign.
     */
    public String sign() {
        return _sign;
    }

    //----------------------------------------
    // Enumeration Functions
    //----------------------------------------

    /**
     * Constructor
     * @param sign The sign.
     */
    COpType(String sign) {
        _sign = sign;
    }
}
