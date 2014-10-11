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
public class Task {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the sql request.
     */
    private SQLRoot _sql = null;


    /**
     * Saves the task data.
     */
    private TaskData _data = null;


    /**
     * Saves the task config.
     */
    private TaskConfig _config = null;


    /**
     * Saves the task infos.
     */
    private TaskInfo _info = null;


    /**
     * Saves the database access.
     */
    private DBAccess _dbAccess = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets the sql request.
     */
    public SQLRoot getSQL() {
        return _sql;
    }


    /**
     * Gets the task data.
     */
    public TaskData getData() {
        return _data;
    }


    /**
     * Gets the task config.
     */
    public TaskConfig getConfig() {
        return _config;
    }


    /**
     * Gets the task info.
     */
    public TaskInfo getInfo() {
        return _info;
    }


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
     * @param sql The sql request.
     * @param dbAccess The database access.
     */
    public Task(SQLRoot sql, DBAccess dbAccess) {

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
    public void error(String message) {
        System.out.println("Error: " + message);
    }
}
