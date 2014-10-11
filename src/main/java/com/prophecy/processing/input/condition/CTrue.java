/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

import com.prophecy.utility.node.Node;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public class CTrue extends Node<CType> implements ICNode {

    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    @Override
    public int getId() {
        return getType().hashCode();
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     */
    public CTrue() {
        super(CType.True);
    }
}
