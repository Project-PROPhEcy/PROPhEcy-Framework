/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.database;

import com.prophecy.database.meta.MetaRelation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * Created by alpha_000 on 06.05.2014.
 */
public class MetaManager {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the database access.
     */
    private DBAccess _dbAccess = null;


    /**
     * Saves the cached relation metadata.
     */
    private Map<String, MetaRelation> _relations = null;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param dbAccess The database access.
     */
    public MetaManager(DBAccess dbAccess) {

        _dbAccess = dbAccess;

    } // Constructor


    /**
     * Gets the metadata for the name specific relation.
     * @param name The relation name.
     * @return The relation metadata.
     */
    public MetaRelation getRelation(String name)
            throws SQLException, ClassNotFoundException {

        // Get the cached relation
        // metadata if it exists.
        /*if(_relations.containsKey(name))
            return _relations.get(name);

        ResultSet columns = null;
        _dbAccess.requestconnection(
            (Connection connection) -> {

                // Get the columns for the name specific relation.
                columns = connection.getMetaData()
                        .getColumns(null, null, name, null);
            }
        );
        Connection conn = _dbAccess
                .requestConnection();



        // Create new neta relation.
        MetaRelation relation = new MetaRelation(name);

        while(columns.next()) {

            // Get the column name.
            String column = columns.getString(4);

            // Add the column to the relation
            // if it's not an event specific column.
            if(!column.contains("E#BID")
                    || !column.contains("E#TID")
                    || !column.contains("E#PROB"))
                relation.addAttributes(column);

        } // while(columns.next()

        // Close the connection.
        conn.close();

        // Cache the relation.
        _relations.put(name, relation);

        return relation;*/
        return null;

    } // getRelation

} // class MetaManager
