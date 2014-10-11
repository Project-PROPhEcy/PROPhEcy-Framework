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
public final class MetaRelation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the relation name.
     */
    private final String _name;


    /**
     * Saves the relation attributes.
     */
    private final Set<String> _attributes
            = new HashSet<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the relation name.
     */
    final public String getName() {
        return _name;
    }


    /**
     * Gets the relation attributes.
     */
    final public Set<String> getAttributes() {
        return _attributes;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param name The relation name.
     */
    public MetaRelation(final String name) {
        _name = name;
    }


    /**
     * Adds the attributes to the relation.
     * @param attrs The attributes.
     */
    final public void addAttributes(final String... attrs) {
        Collections.addAll(_attributes, attrs);
    }
}
