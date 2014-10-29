/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.processing.input.sql.base.ISQLNodeVisitor;
import com.prophecy.processing.input.sql.base.SQLNode;
import com.prophecy.processing.input.term.Attribute;

import java.util.Collections;
import java.util.List;

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLRelation extends SQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private String _name = null;
    private List<Attribute> _attributes = null;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the relation name.
     */
    public String getName() {
        return _name;
    }

    /**
     * Gets the relation attributes
     * without probabilistic columns.
     */
    public List<Attribute> getAttributes() {
        return Collections
                .unmodifiableList(_attributes);
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Constructor
     * @param name The relation name.
     */
    public SQLRelation(String name) {
        _name = name;
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public final void accept(final ISQLNodeVisitor visitor) {
        visitor.visit(this);
    }
}
