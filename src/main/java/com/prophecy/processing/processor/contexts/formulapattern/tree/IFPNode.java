/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.formulapattern.tree;

import com.prophecy.processing.input.condition.ICNode;
import com.prophecy.utility.node.INode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alpha_000 on 27.05.2014.
 */
public interface IFPNode extends INode<FPType> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the formula pattern head attributes.
     */
    public List<String> getHeadAttrs() throws Exception;

    /**
     * Gets the formula pattern sources.
     */
    public Map<Integer, FPSource> getSources();

    /**
     * Gets the construction condition.
     */
    public ICNode getCondition();

    /**
     * Determines whether the lineage
     * nodes should be factorized.
     */
    public boolean isFactorized();

    /**
     * Gets the formula pattern id. Equal
     * formula patterns has the same id.
     */
    public int getId();


    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * Determines whether this formula pattern
     * contains the specific source id.
     * @param sourceId The source id.
     * @return The boolean value.
     */
    default public boolean containsSourceId(int sourceId) {
        return getSources().keySet().contains(sourceId);
    }
}
