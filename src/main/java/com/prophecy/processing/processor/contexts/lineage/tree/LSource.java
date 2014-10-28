/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.processing.processor.contexts.lineage.Event;
import com.prophecy.processing.processor.contexts.lineage.tree.base.ILNodeVisitor;
import com.prophecy.processing.processor.contexts.lineage.tree.base.LNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by alpha_000 on 27.06.2014.
 */
public final class LSource extends LNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------

    private int _maskPriority = 0;
    private final Set<Event> _events
            = new HashSet<>();

    //----------------------------------------
    // Class Properties
    //----------------------------------------

    /**
     * Gets the mask priority.
     */
    public final int getMaskPriority() {
        return _maskPriority;
    }

    /**
     * Sets the mask priority.
     */
    public final void setMaskPriority(final int value) {
        _maskPriority = value;
    }

    /**
     * Gets the number of events, stored in this source.
     */
    public final int getEventCount() {
        return _events.size();
    }

    /**
     * Gets all events, which are relevant for this source.
     */
    public final Set<Event> getEvents() {
        return Collections.unmodifiableSet(_events);
    }

    //----------------------------------------
    // Class Functions
    //----------------------------------------

    /**
     * Adds the event to this source.
     * @param event The event.
     */
    public final void add(final Event event) {

        // Add the source
        // to the parents.
        event.addParent(this);

        // Add the event to
        // the source.
        _events.add(event);
    }

    /**
     * Allows a visitor access to the specific object and it's data.
     * @param visitor The visitor instance.
     */
    @Override
    public void accept(ILNodeVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {
        return String.format("%s ( %s )"
                , _events.stream().map(
                        (Event event) -> String.format("%d.%d", event.getBID(), event.getTID()))
                        .collect(Collectors.joining(" | "))
                , toString());
    }

    /**
     * Returns the string representation.
     * @return The string representation.
     */
    @Override
    public final String toString() {
        return "Source";
    }
}
