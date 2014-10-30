/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql.base;

import com.prophecy.database.DBAccess;

/**
 * Created by Christian Winkel on 23.04.14.
 */
public abstract class SQLNode implements ISQLNodeVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Prepares the sql node for the specific database access.
     * @param dbAccess The database access.
     */
    public void prepareFor(DBAccess dbAccess) {

    }
}