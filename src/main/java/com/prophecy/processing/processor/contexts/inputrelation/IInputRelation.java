/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.inputrelation;

/**
 * Created by alpha_000 on 02.05.2014.
 */
public interface IInputRelation extends Iterable<DomainTuple> {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Determines whether this input relation represents a type 3.
     */
    public boolean isType3();


    /**
     * Gets the responsible source id for this type 3 input relation.
     */
    public int getType3SourceId();


    /**
     * Gets the sql query which is used to create the result set.
     */
    public String getSqlQuery();


    //----------------------------------------
    // Interface Functions
    //----------------------------------------


    /**
     * This functions prepares the input relation
     * to return an iterator for looping.
     */
    public void prepareNextIteration() throws Exception;
}
