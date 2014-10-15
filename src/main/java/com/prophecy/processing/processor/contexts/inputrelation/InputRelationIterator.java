package com.prophecy.processing.processor.contexts.inputrelation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * Created by alpha_000 on 02.06.2014.
 */
public final class InputRelationIterator implements Iterator<DomainTuple> {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the responsible result set.
     */
    private final ResultSet _resultSet;


    /**
     * Saves the responsible source id for this type 3 input relation.
     */
    private final int _type3SourceId;


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Determines whether there is another
     * domain tuple available.
     */
    @Override
    public final boolean hasNext() {
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
    public InputRelationIterator(final ResultSet resultSet, final int type3SourceId) {
        _resultSet = resultSet;
        _type3SourceId = type3SourceId;
    }


    /**
     * Gets the next domain tuple.
     * @return The next domain tuple.
     */
    @Override
    public final DomainTuple next() {
        // TODO nicht imemr ein neues Domain Tuple erzeugen
        return new DomainTuple(
                _resultSet, _type3SourceId);
    }
}
