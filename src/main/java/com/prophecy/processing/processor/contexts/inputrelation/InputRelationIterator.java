package com.prophecy.processing.processor.contexts.inputrelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by alpha_000 on 02.06.2014.
 */
public class InputRelationIterator implements Iterator<DomainTuple> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the responsible result set.
     */
    private ResultSet _resultSet = null;


    /**
     * Saves the responsible source id for this type 3 input relation.
     */
    private int _type3SourceId = -1;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Determines whether there is another
     * domain tuple available.
     */
    @Override
    public boolean hasNext() {
        try {
            return _resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Constructor
     * @param resultSet The responsible result set.
     * @param type3SourceId The responsible source id for this type 3 input relation.
     */
    public InputRelationIterator(ResultSet resultSet, int type3SourceId) {
        _resultSet = resultSet;
        _type3SourceId = type3SourceId;
    }


    /**
     * Gets the next domain tuple.
     * @return The next domain tuple.
     */
    @Override
    public DomainTuple next() {
        return new DomainTuple(
                _resultSet, _type3SourceId);
    }
}
