/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.construction;

import com.prophecy.processing.Task;
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

import java.sql.SQLException;
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

                task.getInfo().measureTime("Lineage", () -> {
                    for (final FPSource source : fpRoot.getSources().values()) {

                        final int sourceId = source.getSourceId();
                        if (d.getSourceType(sourceId) == 1
                                || d.getSourceType(sourceId) == 2) {

                            fpRoot.accept(this, null, d, sourceId);
                            
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
    private LNode setupNode(final FPNode fp, final DomainTuple d, final LNode parent) {

        ConditionInterpreter cInterpreter
                = new ConditionInterpreter(fp.getCondition(), d);

        if( ! cInterpreter.evaluate()) {
            return new LFalse();
        }

        final FactorCatalog factCat
                = getFactorCatalog(fp.getId());

        GenTuple key = null;
        try {
            key = (fp.isFactorized() || parent == null)
                    ? GenTuple.From(fp, d)
                    : GenTuple.From(fp, d, parent.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(factCat.contains(key))
            return factCat.get(key);

        return factCat.put(key, fp.createLineageNode());
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

        final LBAnd lAnd = (LBAnd) lNode;
        if(fpAnd.getLeftChild().containsSourceId(sourceId)) {
            if(lAnd.getLeftChild() instanceof LFalse) {
                final LNode child = setupNode(
                        fpAnd.getLeftChild(), d, lNode);
                lAnd.setLeftChild(child);
            }

            fpAnd.getLeftChild().accept(
                    this, lAnd.getLeftChild(), d, sourceId);
        }

        if(fpAnd.getRightChild().containsSourceId(sourceId)) {
            if(lAnd.getRightChild() instanceof LFalse) {
                final LNode child = setupNode(
                        fpAnd.getRightChild(), d, lNode);
                lAnd.setRightChild(child);
            }

            fpAnd.getRightChild().accept(
                    this, lAnd.getRightChild(), d, sourceId);
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

        final LBOr lOr = (LBOr) lNode;
        if(fpOr.getLeftChild().containsSourceId(sourceId)) {
            if(lOr.getLeftChild() instanceof LFalse) {
                final LNode child = setupNode(
                        fpOr.getLeftChild(), d, lNode);
                lOr.setLeftChild(child);
            }

            fpOr.getLeftChild().accept(
                    this, lOr.getLeftChild(), d, sourceId);
        }

        if(fpOr.getRightChild().containsSourceId(sourceId)) {
            if(lOr.getRightChild() instanceof LFalse) {
                final LNode child = setupNode(
                        fpOr.getRightChild(), d, lNode);
                lOr.setRightChild(child);
            }

            fpOr.getRightChild().accept(
                    this, lOr.getRightChild(), d, sourceId);
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

        final LNOr lOr = (LNOr) lNode;
        final LNode child = setupNode(
                fpNOr.getChild(), d, lNode);

        if( ! lOr.contains(child) )
            lOr.insert(child);

        fpNOr.getChild().accept(
                this, child, d, sourceId);
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

        final LUNot lNot = (LUNot) lNode;
        if(lNot.getChild() instanceof LFalse) {
            final LNode child = setupNode(
                    fpNot.getChild(), d, lNode);
            lNot.setChild(child);
        }

        fpNot.getChild().accept(
                this, lNot.getChild(), d, sourceId);
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

        final LSource lSource = (LSource) lNode;
        try {
            if(d.getSourceType(sourceId) == 2) {

                final Event event = _eventManager.create(
                        d.getBID(sourceId),
                        d.getTID(sourceId),
                        d.getPROB(sourceId)
                );

                lSource.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}