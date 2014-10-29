/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.input.sql;

import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.sql.base.ISQLNodeVisitor;
import com.prophecy.processing.input.sql.base.SQLNode;

/**
 * Created by alpha_000 on 13.05.2014.
 */
public final class SQLIJoin extends SQLNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private String _leftNS = null;
    private String _rightNS = null;
    private CNode _condition = null;

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the left child namespace.
     */
    public final String getLeftNS() {
        return _leftNS;
    }

    /**
     * Gets the right child namespace.
     */
    public final String getRightNS() {
        return _rightNS;
    }

    /**
     * Gets the selection condition.
     */
    public CNode getCondition() {
        return _condition;
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
