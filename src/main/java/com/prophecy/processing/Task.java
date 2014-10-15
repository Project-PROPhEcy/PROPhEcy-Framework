/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing;

import com.prophecy.database.DBAccess;
import com.prophecy.processing.input.sql.SQLRoot;

/**
 * Created by alpha_000 on 04.05.2014.
 */
public final class Task {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the sql request.
     */
    private final SQLRoot _sql;


    /**
     * Saves the task data.
     */
    private final TaskData _data;


    /**
     * Saves the task config.
     */
    private final TaskConfig _config;


    /**
     * Saves the task infos.
     */
    private final TaskInfo _info;


    /**
     * Saves the database access.
     */
    private final DBAccess _dbAccess;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the sql request.
     */
    public final SQLRoot getSQL() {
        return _sql;
    }


    /**
     * Gets the task data.
     */
    public final TaskData getData() {
        return _data;
    }


    /**
     * Gets the task config.
     */
    public final TaskConfig getConfig() {
        return _config;
    }


    /**
     * Gets the task info.
     */
    public final TaskInfo getInfo() {
        return _info;
    }


    /**
     * Gets the database access.
     */
    public final DBAccess getDBAccess() {
        return _dbAccess;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param sql The sql request.
     * @param dbAccess The database access.
     */
    public Task(final SQLRoot sql, final DBAccess dbAccess) {

        _sql = sql;
        _dbAccess = dbAccess;

        _data = new TaskData();
        _config = new TaskConfig();
        _info = new TaskInfo();
    }


    /**
     * The task can't be completed because of error.
     * @param message The error message.
     */
    public final void error(final String message) {
        System.out.println("Error: " + message);
    }
}
