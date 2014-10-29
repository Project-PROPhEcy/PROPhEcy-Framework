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

/**
 * Created by alpha_000 on 03.05.2014.
 */
public class SQLNJoin extends SQLNode {

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public final void accept(final ISQLNodeVisitor visitor) {
        visitor.visit(this);
    }
}
