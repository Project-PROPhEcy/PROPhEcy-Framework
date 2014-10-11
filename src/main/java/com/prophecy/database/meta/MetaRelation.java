/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.database.meta;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 06.05.2014.
 */
public class MetaRelation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the relation name.
     */
    private String _name = null;


    /**
     * Saves the relation attributes.
     */
    private Set<String> _attributes
            = new HashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the relation name.
     */
    public String getName() {
        return _name;
    }


    /**
     * Gets the relation attributes.
     */
    public Set<String> getAttributes() {
        return _attributes;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param name The relation name.
     */
    public MetaRelation(String name) {
        _name = name;
    }


    /**
     * Adds the attributes to the relation.
     * @param attrs The attributes.
     */
    public void addAttributes(String... attrs) {
        Collections.addAll(_attributes, attrs);
    }
}
