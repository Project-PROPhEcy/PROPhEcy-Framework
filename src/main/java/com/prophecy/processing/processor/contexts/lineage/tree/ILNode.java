/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.utility.node.INode;

import java.util.Set;

/**
 * Created by alpha_000 on 27.06.2014.
 */
public interface ILNode extends INode<LType> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the parent nodes.
     */
    public Set<ILNode> getParents();


    /**
     * Determines whether this node is a root.
     */
    public default boolean isRoot() {
        return getParents().size() == 0;
    }


    /**
     * Gets the mask level of current calculations.
     */
    public int getMaskLevel();


    /**
     * Sets the mask level of current calculations.
     */
    public void setMaskLevel(int value);


    /**
     * Gets the current calculated probability.
     */
    public Double getCurrentProb();


    /**
     * Sets the current calculation probability.
     */
    public void setCurrentProb(Double value);


    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * Adds the parent node to the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public boolean addParent(ILNode parent);

    /**
     * Removes the parent node from the set.
     * @param parent The parent node.
     * @return The boolean value.
     */
    public boolean removeParent(ILNode parent);

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    public String toTreeString();
}
