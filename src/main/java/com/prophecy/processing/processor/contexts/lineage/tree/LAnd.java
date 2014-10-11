/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.tree;

/**
 * Created by alpha_000 on 27.06.2014.
 */
public final class LAnd extends LNNode {

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param unique The unique state.
     */
    public LAnd(final boolean unique) {
        super(LType.And, unique, new LFalse());
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
