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

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class CFalse extends CNode {

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    @Override
    public final int getId() {
        return CFalse.class.hashCode();
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public final void accept(final ICNodeVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return toString();
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "F";
    }
}
