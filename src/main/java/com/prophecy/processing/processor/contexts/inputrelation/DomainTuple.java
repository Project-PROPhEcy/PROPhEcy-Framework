/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.inputrelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class DomainTuple {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves teh specific result set.
     */
    private ResultSet _resultSet = null;


    /**
     * Saves the responsible source id for this type 3 domain tuple.
     */
    private int _type3SourceId = -1;

    /**
     * Used to cache the head attributes.
     */
    private HashMap<String, Object> _headAttrs
            = new HashMap<>();


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param resultSet The specific result set.
     * @param type3SourceId The responsible source id for this domain tuple.
     */
    public DomainTuple(ResultSet resultSet, int type3SourceId) {
        _resultSet = resultSet;
        _type3SourceId = type3SourceId;
    }


    /**
     * Gets the name specific attribute.
     * @param name The attribute name.
     * @return The attribute or default.
     */
    public Object getAttr(String name)
            throws SQLException {
        return getAttr(name, null);
    }


    /**
     * Gets the name specific attribute.
     * @param name The attribute name.
     * @return The attribute or default.
     */
    public Object getAttr(String name, Object otherwise)
            throws SQLException {

        if( ! _headAttrs.containsKey( name ) )
            _headAttrs.put( name, _resultSet.getObject(name) );

        return _headAttrs.getOrDefault(name, otherwise);
    }


    /**
     * Gets the source id specific type.
     * @param sourceId The source id.
     * @return The source type.
     */
    public int getSourceType(int sourceId)
            throws SQLException {

        if(_type3SourceId == -1
                || sourceId == _type3SourceId)
            return _resultSet.getInt(String.format("E#S%s", sourceId));

        return 0;
    }


    /**
     * Gets the source id specific block id.
     * @param sourceId The source id.
     * @return The block id.
     */
    public int getBID(int sourceId)
            throws SQLException {
        return _resultSet.getInt(
                String.format("E#BID%s", sourceId));
    }


    /**
     * Gets the source id specific tuple id.
     * @param sourceId The source id.
     * @return The tuple id.
     */
    public int getTID(int sourceId)
            throws SQLException {
        return _resultSet.getInt(
                String.format("E#TID%s", sourceId));
    }


    /**
     * Gets the source id specific probability.
     * @param sourceId The source id.
     * @return The probability.
     */
    public Double getPROB(int sourceId)
            throws SQLException {
        return _resultSet.getDouble(
                String.format("E#PROB%s", sourceId));
    }
}
