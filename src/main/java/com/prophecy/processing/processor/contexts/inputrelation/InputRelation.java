/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.inputrelation;

import com.prophecy.database.DBAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by alpha_000 on 28.05.2014.
 */
public class InputRelation implements IInputRelation {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the database access object.
     */
    private DBAccess _dbAccess = null;


    /**
     * Saves the sql query which is used to create the result set.
     */
    private String _sqlQuery = null;


    /**
     * Saves the responsible source id for this type 3 input relation.
     */
    private int _type3SourceId = -1;


    /**
     * Saves the next input relation results.
     */
    private ResultSet _nextResults = null;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Determines whether this input relation represents a type 3.
     */
    @Override
    public boolean isType3() {
        return _type3SourceId != -1;
    }

    /**
     * Gets the responsible source id for this type 3 input relation.
     */
    @Override
    public int getType3SourceId() {
        return _type3SourceId;
    }

    /**
     * Gets the sql query which is used to create the result set.
     */
    @Override
    public String getSqlQuery() {
        return _sqlQuery;
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param dbAccess The database access object.
     * @param sqlQuery The sql query which is used to create the result set.
     */
    public InputRelation(DBAccess dbAccess, String sqlQuery) {
        this(dbAccess, sqlQuery, -1);
    }


    /**
     * Constructor
     * @param dbAccess The database access object.
     * @param sqlQuery The sql query which is used to create the result set.
     * @param type3SourceId The responsible source id for this type 3 input relation.
     */
    public InputRelation(DBAccess dbAccess, String sqlQuery, int type3SourceId) {

        _dbAccess = dbAccess;
        _sqlQuery = sqlQuery;
        _type3SourceId = type3SourceId;
    }


    /**
     * This functions prepares the input relation
     * to return an iterator for looping.
     */
    @Override
    public void prepareNextIteration() throws Exception {

        _dbAccess.requestConnection(
                (Connection connection) -> {

                Statement statement = connection
                        .createStatement();

                _nextResults = statement
                        .executeQuery(_sqlQuery);
            }
        );
    }


    /**
     * Gets the responsible domain tuple iterator.
     * @return The iterator.
     */
    @Override
    public Iterator<DomainTuple> iterator() {

        if(_nextResults == null)
            return null;

        InputRelationIterator it = new InputRelationIterator(
                _nextResults, _type3SourceId);

        _nextResults = null;
        return it;
    }
}
