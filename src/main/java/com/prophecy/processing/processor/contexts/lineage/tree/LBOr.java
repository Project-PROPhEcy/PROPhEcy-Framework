package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.processing.processor.contexts.lineage.tree.base.ILNodeVisitor;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LBNode;

/**
 * Created by alpha_000 on 28.10.2014.
 */
public final class LBOr extends LBNode {

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public final void accept(final ILNodeVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "Or";
    }
}
