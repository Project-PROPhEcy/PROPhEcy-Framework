/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.input.term.ITerm;
import com.prophecy.utility.node.UNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLProjection extends UNode<SQLType, ISQLNode> implements ISQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the projected attributes.
     */
    private List<ITerm> _attributes = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the projected Attributes.
     */
    public List<ITerm> getAttributes() {
        return Collections
                .unmodifiableList(_attributes);
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     */
    public SQLProjection() {
        super(SQLType.Projection);
    }


    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    @Override
    public void prepareFor(DBAccess dbAccess) {

    }
}
