/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.database.DBAccess;
import com.prophecy.utility.node.BNode;

import java.util.Arrays;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLNJoin extends BNode<SQLType, ISQLNode> implements ISQLNode {

    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     */
    public SQLNJoin() {
        super(SQLType.NJoin);
    }


    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    @Override
    public void prepareFor(DBAccess dbAccess) {

    }
}
