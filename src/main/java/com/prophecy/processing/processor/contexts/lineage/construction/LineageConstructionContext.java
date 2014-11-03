/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.Task;
import com.prophecy.processing.input.condition.*;
import com.prophecy.processing.input.condition.base.CNode;
import com.prophecy.processing.input.condition.base.ICNodeVisitor;
import com.prophecy.processing.input.term.Attribute;
import com.prophecy.processing.input.term.Value;
import com.prophecy.processing.processor.IProcessorContext;
import com.prophecy.processing.processor.ProcessorInfo;
import com.prophecy.processing.processor.contexts.formulapattern.tree.*;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.FPNode;
import com.prophecy.processing.processor.contexts.formulapattern.tree.base.IFPNodeVisitor;
import com.prophecy.processing.processor.contexts.inputrelation.DomainTuple;
import com.prophecy.processing.processor.contexts.inputrelation.IInputRelation;
import com.prophecy.processing.processor.contexts.inputrelation.InputRelationList;
import com.prophecy.processing.processor.contexts.lineage.Event;
import com.prophecy.processing.processor.contexts.lineage.EventManager;
import com.prophecy.processing.processor.contexts.lineage.tree.*;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;
import com.prophecy.utility.ListUtils;
import com.prophecy.utility.Reference;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alpha_000 on 02.05.2014.
 */
@ProcessorInfo(name = "Lineage Construction Processor", config = {})
public final class LineageConstructionContext implements IProcessorContext, IFPNodeVisitor {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private final Map<Integer, FactorCatalog> _catalogs
            = new HashMap<>();

    private final EventManager _eventManager
            = new EventManager();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Counts the number of nodes.
     * @return The number of nodes.
     */
    public final int getNodeCount() {
        return ListUtils.FoldLeft(
                0, _catalogs.values(), (a, b) -> a + b.size());
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Runs the processor context with the specific task.
     * @param task The task.
     */
    @Override
    public final void run(Task task)
            throws Exception {

        final InputRelationList relList = task.getData()
                .require(InputRelationList.class);
        final FPNode fpRoot = task.getData()
                .require(FPNode.class);

        for(final IInputRelation rel: relList) {

            rel.prepareNextIteration();
            for (final DomainTuple d : rel) {
                _currentDomainTuple = d;
                task.getInfo().measureTime("Lineage", () -> {
                    for (final FPSource source : fpRoot.getSources().values()) {

                        final int sourceId = source.getSourceId();
                        if (d.getSourceType(sourceId) == 1
                                || d.getSourceType(sourceId) == 2) {

                            insertPath(null, fpRoot, d, sourceId);
                        } else if (d.getSourceType(sourceId) == 3) {

                            // Specific source needs to be factorized
                            // for the source-type != 2, because otherwise
                            // there is a parent node required for the gentuple.
                            if (!source.isFactorized())
                                throw new Exception(String.format(
                                        "The source with relation %s and id: %d " +
                                                "needs to be factorized to use source types != 2.",
                                        source.getRelation(), source.getSourceId()
                                ));

                            final FactorCatalog factCat
                                    = getFactorCatalog(source.getId());
                            final GenTuple key = GenTuple.From(source, d);

                            // Filter all type 3 entries
                            // which aren't relevant.
                            if (!factCat.contains(key))
                                break;

                            final LSource lSource = (LSource)
                                    factCat.get(key);

                            final Event event = _eventManager.create(
                                    d.getBID(sourceId),
                                    d.getTID(sourceId),
                                    d.getPROB(sourceId)
                            );

                            lSource.add(event);
                        }
                    }
                });

                _currentDomainTuple = null;
            }
        }

        // Todo Event count hier auch erledigen und nicht in calculation
        task.getInfo().setInfo("Unique Events", _eventManager.size());
        task.getInfo().setInfo("Nodes", getNodeCount());
        task.getData().insert(FactorCatalog.class,
                getFactorCatalog(fpRoot.getId()));
        task.getData().insert(EventManager.class,
                _eventManager);
    }


    /**
     * Configures the lineage node for the specific formula pattern node.
     * @param fp The formula pattern node.
     * @param d The domain tuple.
     * @param parent The parent node.
     * @return The configured lineage node.
     */
    private ILNode setupNode(final FPNode fp, final DomainTuple d, final ILNode parent)
            throws Exception {

        if( ! evaluate( fp.getCondition(), d ) ) {
            return new LFalse();
        }

        final FactorCatalog factCat
                = getFactorCatalog(fp.getId());

        final GenTuple key = (fp.isFactorized() || parent == null)
                ? GenTuple.From(fp, d)
                : GenTuple.From(fp, d, parent.hashCode());

        if(factCat.contains(key))
            return factCat.get(key);

        switch(fp.getType()) {

            case NOr: return factCat.put(key, new LOr(true));
            case Not: return factCat.put(key, new LUNot());
            case And: {

                final LAnd lAnd = new LAnd(false);

                // We explicit need to set two child nodes,
                // because NNode sets only one default node.
                lAnd.addChild(new LFalse());
                lAnd.addChild(new LFalse());

                return factCat.put(key, lAnd);
            }
            case Or: {

                final LOr lOr = new LOr(false);

                // We explicit need to set two child nodes,
                // because NNode sets only one default node.
                lOr.addChild(new LFalse());
                lOr.addChild(new LFalse());

                return factCat.put(key, lOr);
            }
            case Source: {

                final FPSource fpSource = (FPSource)fp;
                final LSource lSource = new LSource();

                // The mask priority is later used
                // for the probability calculation
                // in order to decide which bid's
                // need to be masked first.

                lSource.setMaskPriority(
                        fpSource.getMaskPriority());

                return factCat.put(key, lSource);
            }
            default:

                throw new Exception(String.format(
                        "Unknown formula pattern type: %s.",
                                fp.getType()));
        }
    }


    /**
     * Gets the id specific factor catalog.
     * @param id The id.
     * @return The factor catalog.
     */
    private FactorCatalog getFactorCatalog(final int id) {

        if(!_catalogs.containsKey(id))
            _catalogs.put(id, new FactorCatalog());

        return _catalogs.get(id);
    }


    /**
     * Evaluates the condition tree with the specific domain tuple.
     * @param condition The condition tree.
     * @param d The domain tuple.
     * @return The boolean value.
     */
    private boolean evaluate(final CNode condition, final DomainTuple d)
            throws Exception {

        switch(condition.getType()) {
            case And:

                final CAnd and = (CAnd) condition;

                return evaluate(and.getLeftChild(), d)
                        && evaluate(and.getRightChild(), d);

            case Or:

                final COr or = (COr) condition;

                return evaluate(or.getLeftChild(), d)
                        || evaluate(or.getRightChild(), d);

            case Not:

                final CNot not = (CNot) condition;

                return !evaluate(not.getChild(), d);

            case Op:

                final COp op = (COp) condition;

                Object value1;
                Object value2;

                if(op.getLTerm() instanceof Attribute)
                    value1 = d.getAttr(((Attribute) op.getLTerm()).getName());
                else if(op.getLTerm() instanceof Value)
                    value1 = ((Value) op.getLTerm()).getInner();
                else
                    throw new Exception(
                            String.format("Unknown term type: %s",
                                    op.getLTerm().getClass()));

                if(op.getRTerm() instanceof Attribute)
                    value2 = d.getAttr(((Attribute) op.getRTerm()).getName());
                else if(op.getRTerm() instanceof Value)
                    value2 = ((Value) op.getRTerm()).getInner();
                else
                    throw new Exception(
                            String.format("Unknown term type: %s",
                                    op.getRTerm().getClass()));

                if(value1 instanceof BigDecimal)
                    value1 = ((BigDecimal) value1).doubleValue();
                if(value2 instanceof BigDecimal)
                    value2 = ((BigDecimal) value2).doubleValue();
                if(value1 instanceof Integer)
                    value1 = ((Integer) value1).doubleValue();
                if(value2 instanceof Integer)
                    value2 = ((Integer) value2).doubleValue();

                switch(op.getOpType()) {

                    case Equal: return value1.equals(value2);
                    case Unequal: return !value1.equals(value2);
                    case Greater: return ((Double)value1) > ((Double)value2);
                    case Less: return ((Double)value1) < ((Double)value2);
                    case GreaterEqual: return ((Double)value1) >= ((Double)value2);
                    case LessEqual: return ((Double)value1) <= ((Double)value2);

                    default:

                        throw new Exception(
                                String.format("Unknown operation type: %s.",
                                        op.getOpType()));
                }

            case True:

                return true;

            case False:

                return false;

            default:

                throw new Exception(
                        String.format("Unknown condition type: %s.",
                                condition.getType()));
        }
    }

    /**
     * Visits the formula pattern and-node.
     * @param fpAnd The formula pattern and-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    @Override
    public void visit(FPAnd fpAnd, LNode lNode, DomainTuple d, int sourceId) {

        // If lNode is null, we have an root
        // node, which needs to be created.
        if(lNode == null)
            lNode = setupNode(fpAnd, d, null);

        // The lineage type could be false
        // because of false condition in setupNode.
        if(lNode instanceof LFalse)
            return;

        final FPAnd fpAnd = (FPAnd) fp;
        final LAnd lAnd = (LAnd) lin;

        if(fpAnd.getLeftChild().containsSourceId(sourceId)) {
            if(lAnd.getChild(0).getType() == LType.False) {

                final ILNode child = setupNode(
                        fpAnd.getLeftChild(), d, lin);
                lAnd.replaceChild(lAnd.getChild(0), child);
            }

            insertPath(lAnd.getChild(0), fpAnd
                    .getLeftChild(), d, sourceId);
        }

        if(fpAnd.getRightChild().containsSourceId(sourceId)) {
            if(lAnd.getChild(1).getType() == LType.False) {

                final ILNode child = setupNode(
                        fpAnd.getRightChild(), d, lin);
                lAnd.replaceChild(lAnd.getChild(1), child);
            }

            insertPath(lAnd.getChild(1), fpAnd
                    .getRightChild(), d, sourceId);
        }
    }

    /**
     * Visits the formula pattern or-node.
     * @param fpOr The formula pattern or-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    @Override
    public void visit(FPOr fpOr, LNode lNode, DomainTuple d, int sourceId) {

        // If lNode is null, we have an root
        // node, which needs to be created.
        if(lNode == null)
            lNode = setupNode(fpOr, d, null);

        // The lineage type could be false
        // because of false condition in setupNode.
        if(lNode instanceof LFalse)
            return;

        final FPOr fpOr = (FPOr) fp;
        final LOr lOr = (LOr) lin;

        if(fpOr.getLeftChild().containsSourceId(sourceId)) {
            if(lOr.getChild(0).getType() == LType.False) {

                final ILNode child = setupNode(
                        fpOr.getLeftChild(), d, lin);
                lOr.replaceChild(lOr.getChild(0), child);
            }

            insertPath(lOr.getChild(0), fpOr
                    .getLeftChild(), d, sourceId);
        }

        if(fpOr.getRightChild().containsSourceId(sourceId)) {
            if(lOr.getChild(1).getType() == LType.False) {

                final ILNode child = setupNode(
                        fpOr.getRightChild(), d, lin);
                lOr.replaceChild(lOr.getChild(1), child);
            }

            insertPath(lOr.getChild(1), fpOr
                    .getRightChild(), d, sourceId);
        }
    }

    /**
     * Visits the formula pattern n-are or-node.
     * @param fpNOr The formula pattern n-are or-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    @Override
    public void visit(FPNOr fpNOr, LNode lNode, DomainTuple d, int sourceId) {

        // If lNode is null, we have an root
        // node, which needs to be created.
        if(lNode == null)
            lNode = setupNode(fpNOr, d, null);

        // The lineage type could be false
        // because of false condition in setupNode.
        if(lNode instanceof LFalse)
            return;

        final FPNOr fpNOr = (FPNOr) fp;
        final LOr lOr = (LOr) lin;

        final ILNode child = setupNode(
                fpNOr.getChild(), d, lin);

        if( ! lOr.containsChild(child) )
            lOr.addChild(child);

        insertPath(child, fpNOr
                .getChild(), d, sourceId);
    }

    /**
     * Visits the formula pattern not-node.
     * @param fpNot The formula pattern not-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    @Override
    public void visit(FPNot fpNot, LNode lNode, DomainTuple d, int sourceId) {

        // If lNode is null, we have an root
        // node, which needs to be created.
        if(lNode == null)
            lNode = setupNode(fpNot, d, null);

        // The lineage type could be false
        // because of false condition in setupNode.
        if(lNode instanceof LFalse)
            return;

        final FPNot fpNot = (FPNot) fp;
        final LUNot lNot = (LUNot) lin;

        if(lNot.getChild().getType() == LType.False) {

            final ILNode child = setupNode(
                    fpNot.getChild(), d, lin);
            lNot.setChild(child);
        }

        insertPath(lNot.getChild(),
                fpNot.getChild(), d, sourceId);
    }

    /**
     * Visits the formula pattern source-node.
     * @param fpSource The formula pattern source-node.
     * @param lNode The lineage node.
     * @param d The current domain tuple.
     * @param sourceId The current source id.
     */
    @Override
    public void visit(FPSource fpSource, LNode lNode, DomainTuple d, int sourceId) {

        // If lNode is null, we have an root
        // node, which needs to be created.
        if(lNode == null)
            lNode = setupNode(fpSource, d, null);

        // The lineage type could be false
        // because of false condition in setupNode.
        if(lNode instanceof LFalse)
            return;

        final LSource lSource = (LSource) lin;

        if(d.getSourceType(sourceId) == 2) {

            final Event event = _eventManager.create(
                    d.getBID(sourceId),
                    d.getTID(sourceId),
                    d.getPROB(sourceId)
            );

            lSource.add(event);
        }
    }
}