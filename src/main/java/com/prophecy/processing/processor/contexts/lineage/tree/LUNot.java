/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.processing.processor.contexts.lineage.tree.base.ILNodeVisitor;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LUNode;

/**
 * Created by alpha_000 on 27.06.2014.
 */
public final class LUNot extends LUNode {

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public void accept(ILNodeVisitor visitor) {
        visitor.visit(this);
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
