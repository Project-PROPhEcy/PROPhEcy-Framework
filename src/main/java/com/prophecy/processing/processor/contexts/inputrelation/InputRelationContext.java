/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.inputrelation;

import com.prophecy.processing.Task;
import com.prophecy.processing.input.sql.*;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.processor.IProcessorContext;
import com.prophecy.processing.input.condition.*;
import com.prophecy.processing.processor.ProcessorInfo;

import java.sql.ResultSet;

/**
 * Created by alpha_000 on 03.05.2014.
 */
@ProcessorInfo(name = "Input Relation Processor", config = {})
public class InputRelationContext implements IProcessorContext {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the attribute manager for
     * managing the virtual attribute names.
     */
    private AttributeManager _attrManger = null;


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Runs the processor context with the specific task.
     * @param task The task.
     */
    @Override
    public void run(Task task)
            throws Exception {

        SQLRoot sql = task.getSQL();

        // The attribute manager is used to index
        // the attributes and make them unique.
        _attrManger = new AttributeManager(sql);
    }


    /**
     * Constructs the input relation request.
     * @param sql The current sql node.
     * @return The input relation request.
     */
    private String constructIRRequest(ISQLNode sql)
            throws Exception {

        switch(sql.getType()) {
            case Root: {

                SQLRoot root = (SQLRoot) sql;

                // Root can be ignored so just
                // return the input relation
                // request from the child node.
                return constructIRRequest(
                        root.getChild());
            }
            case Projection: {

                SQLProjection projection
                        = (SQLProjection) sql;

                // Projection can be ignored so
                // just return the input relation
                // request from child node.
                return constructIRRequest(
                        projection.getChild());
            }
            case Selection: {

                SQLSelection selection
                        = (SQLSelection) sql;

                return String.format("SELECT * FROM ( %s ) WHERE %s",
                        constructIRRequest(selection.getChild()),
                        constructConditionString(selection.getCondition()));
            }
            case Minus: {

                SQLMinus minus = (SQLMinus) sql;

                break;
            }
            case Union: {

                SQLUnion union = (SQLUnion) sql;

                break;
            }
            case NJoin: {

                SQLNJoin njoin = (SQLNJoin) sql;

                return String.format("SELECT * FROM (%s) NATURAL JOIN (%s)",
                        constructIRRequest(njoin.getLeftChild()),
                        constructIRRequest(njoin.getRightChild()));

            }
            case IJoin: {

                // Cast to the specific type.
                SQLIJoin ijoin = (SQLIJoin) sql;

                return String.format("SELECT * FROM (%s) INNER JOIN (%s) ON %s",
                        constructIRRequest(ijoin.getLeftChild()),
                        constructIRRequest(ijoin.getRightChild()),
                        constructConditionString(ijoin.getCondition()));
            }
            case Relation: {

                SQLRelation relation
                        = (SQLRelation) sql;

                String selectAttrs = "";

                for(Attribute attr: relation.getAttributes())
                    selectAttrs += attr + ",";

                //selectAttrs += "E#BID AS E#BID" + relation.getQueryId() + ", ";
                //selectAttrs += "E#TID AS E#TID" + relation.getQueryId() + ", ";
                //selectAttrs += "E#PROB AS E#PROB" + relation.getQueryId();

                return String.format("SELECT %s FROM %s",
                        selectAttrs, relation.getName());
            }
        }

        throw new Exception(String.format(
                "Can't construct the input relation request. " +
                        "The node type %s is unknown.", sql.getType()));
    }


    /**
     * Constructs the condition string.
     * @param node The current condition node.
     * @return The condition string.
     */
    private String constructConditionString(ICNode node)
            throws Exception {

        switch(node.getType()) {
            case And: {

                // Cast to specific type.
                CAnd and = (CAnd) node;

                String left = constructConditionString(and.getLeftChild());
                String right = constructConditionString(and.getRightChild());

                return String.format("(%s) AND (%s)", left, right);
            }
            case Not: {

                // Cast to specific type.
                CNot not = (CNot) node;

                return String.format("NOT (%s)",
                        constructConditionString(not.getChild()));
            }
            case Or: {

                // Cast to specific type.
                COr or = (COr) node;

                String left = constructConditionString(or.getLeftChild());
                String right = constructConditionString(or.getRightChild());

                return String.format("(%s) OR (%s)", left, right);
            }
            case Op:

                // Cast to specific type.
                COp op = (COp)node;

                return String.format("%s %s %s",
                        op.getLTerm(),
                        op.getOpType().getSign(),
                        op.getRTerm()
                );

            default:

                throw new Exception(String.format(
                        "Can't construct the condition string. " +
                                "The node type %s is unknown.", node.getType()));

        }
    }
}