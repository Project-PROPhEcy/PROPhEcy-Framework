/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.utility.node.Node;

import java.util.Collections;
import java.util.List;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLRelation extends Node<SQLType> implements ISQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the relation name.
     */
    private String _name = null;


    /**
     * Saves the relation attributes
     * without probabilistic columns.
     */
    private List<Attribute> _attributes = null;


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
     * Gets the relation attributes
     * without probabilistic columns.
     */
    public List<Attribute> getAttributes() {
        return Collections
                .unmodifiableList(_attributes);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param name The relation name.
     */
    public SQLRelation(String name) {
        super(SQLType.Relation);

        _name = name;
    }


    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    @Override
    public void prepareFor(DBAccess dbAccess) {

    }
}
