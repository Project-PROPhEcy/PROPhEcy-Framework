/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy;

import com.prophecy.database.DBAccessManager;

/**
 * Created by Christian Winkel on 23.04.14.
 */
public class Prophecy {

    //----------------------------------------
    // Static Variables
    //----------------------------------------


    /**
     * Saves the database access manager.
     */
    private static DBAccessManager _DBAccessManager = null;


    //----------------------------------------
    // Static Properties
    //----------------------------------------


    /**
     * Gets the database access manager.
     */
    public static DBAccessManager GetDBAccessManager() {
        return _DBAccessManager;
    }


    //----------------------------------------
    // Static Functions
    //----------------------------------------


    /**
     * Static Constructor
     */
    static {
        _DBAccessManager = new DBAccessManager();
    }
}
