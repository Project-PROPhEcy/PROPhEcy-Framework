/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.input.condition.ICNode;
import com.prophecy.utility.node.UNode;

import java.util.Arrays;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLSelection extends UNode<SQLType, ISQLNode> implements ISQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the selection condition.
     */
    private ICNode _condition = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the selection condition.
     */
    public ICNode getCondition() {
        return _condition;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param condition The selection condition.
     */
    public SQLSelection(ICNode condition) {
        super(SQLType.Selection);

        _condition = condition;
    }


    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    @Override
    public void prepareFor(DBAccess dbAccess) {

    }
}
