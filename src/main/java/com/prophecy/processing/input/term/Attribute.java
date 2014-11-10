package com.prophecy.processing.input.term;

import com.prophecy.processing.input.term.base.ITerm;
import com.prophecy.processing.input.term.base.ITermVisitor;

/**
 * Created by alpha_000 on 05.05.2014.
 */
public final class Attribute implements ITerm {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final String _name;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the attribute name.
     */
    public final String getName() {
        return _name;
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param name The attribute name.
     */
    public Attribute(final String name) {
        _name = name;
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    @Override
    public <ParamT> void accept(ITermVisitor<ParamT> visitor, ParamT param) {
        visitor.visit(this, param);
    }

    /**
     * Gets the sql representation string.
     * @return The sql representation string.
     */
    public final String toSQLString() {
        return getName();
    }
}
