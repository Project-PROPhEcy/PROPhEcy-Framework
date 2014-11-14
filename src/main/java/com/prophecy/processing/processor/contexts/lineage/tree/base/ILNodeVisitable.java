package com.prophecy.processing.processor.contexts.lineage.tree.base;

/**
 * Created by alpha_000 on 30.10.2014.
 */
public interface ILNodeVisitable {

    //----------------------------------------
    // Interface Functions
    //----------------------------------------

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     * @param param A possible parameter.
     */
    public <ParamT> void accept(final ILNodeVisitor<ParamT> visitor, ParamT param);
}
