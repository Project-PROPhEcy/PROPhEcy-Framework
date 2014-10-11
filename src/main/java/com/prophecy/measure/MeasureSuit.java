/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.measure;

import com.prophecy.database.DBAccess;

/**
 * Created by alpha_000 on 15.08.2014.
 */
public class MeasureSuit {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the database access.
     */
    private DBAccess _dbAccess = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the database access.
     */
    public DBAccess getDBAccess() {
        return _dbAccess;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param dbAccess The database access.
     */
    public MeasureSuit(DBAccess dbAccess) {
        _dbAccess = dbAccess;
    }


    /**
     * Runs the specific measure tests.
     * @param groupId The test group id.
     * @param tests The measure tests.
     */
    public void run(String groupId, MeasureTest... tests) {

        System.out.println(String.format(
                "Start Measure Suit with %d Measure Tests.", tests.length));

        for(int i = 1; i <= tests.length; i++) {

            tests[i].run(this);

            System.out.println("---------------------------------------------");
            System.out.println(String.format(
                    "Measure Test %s / %s completed", i, tests.length));
        }
    }
}