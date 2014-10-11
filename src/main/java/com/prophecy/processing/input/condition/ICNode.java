/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.condition;

import com.prophecy.utility.node.INode;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public interface ICNode extends INode<CType> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the condition id. Equal
     * conditions has the same id.
     */
    public int getId();
}
