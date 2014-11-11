/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

import com.prophecy.processing.input.condition.base.CBNode;
import com.prophecy.processing.input.condition.base.ICNodeVisitor;

import java.util.Arrays;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class CAnd extends CBNode {

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    @Override
    public final int getId() {
        return Arrays.asList(CAnd.class
                , getLeftChild().getId()
                , getRightChild().getId()
        ).hashCode();
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    @Override
    public <ParamT> void accept(ICNodeVisitor<ParamT> visitor, ParamT param) {
        visitor.visit(this, param);
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "And";
    }
}
