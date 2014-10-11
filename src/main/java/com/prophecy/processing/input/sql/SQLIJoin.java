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
import com.prophecy.utility.node.BNode;

import java.util.Arrays;

/**
 * Created by alpha_000 on 13.05.2014.
 */
public class SQLIJoin extends BNode<SQLType, ISQLNode> implements ISQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the left child namespace.
     */
    private String _leftNS = null;


    /**
     * Saves the right child namespace.
     */
    private String _rightNS = null;


    /**
     * Saves the selection condition.
     */
    private ICNode _condition = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the left child namespace.
     */
    public String getLeftNS() {
        return _leftNS;
    }


    /**
     * Gets the right child namespace.
     */
    public String getRightNS() {
        return _rightNS;
    }


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
     */
    public SQLIJoin() {
        super(SQLType.IJoin);

    }


    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    @Override
    public void prepareFor(DBAccess dbAccess) {

    }
}
