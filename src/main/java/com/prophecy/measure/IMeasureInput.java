/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.measure;

/**
 * Created by alpha_000 on 15.08.2014.
 */
public interface IMeasureInput {

    //----------------------------------------
    // Interface Properties
    //----------------------------------------


    /**
     * Gets the query name.
     */
    public String getName();


    /**
     * Gets the query typing.
     */
    public String getTyping();


    /**
     * Gets the query engine.
     */
    public String getEngine();
}
