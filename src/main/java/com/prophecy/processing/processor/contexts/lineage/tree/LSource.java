/*
PROPhEcy (c) by Christian Winkel

PROPhEcy is licensed under a
Creative Commons Attribution 4.0 International License.

You should have received a copy of the license along with this
work. If not, see <http://creativecommons.org/licenses/by/4.0/>.
*/

package com.prophecy.processing.processor.contexts.lineage.tree;

import com.prophecy.processing.processor.contexts.lineage.Event;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alpha_000 on 27.06.2014.
 */
public final class LSource extends LNode {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    /**
     * Saves the mask priority.
     */
    private int _maskPriority = 0;


    /**
     * Saves all events, which are relevant for this source.
     */
    private final Set<Event> _events = new HashSet<>();


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
     * Constructor
     */
    public LSource() {
        super(LType.Source);
    }

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
     * Returns the lineage tree representation.
     * @return The lineage tree representation.
     */
    @Override
    public final String toTreeString() {

        final StringBuilder builder = new StringBuilder(
                String.format("( ", toString()));
        boolean first = true;
        for(Event event: _events) {
            if( !first ) {  builder.append( " | " ); }
            builder.append( event.getBID() + "." + event.getTID() );
            first = false;
        }
        builder.append(" )");
        return builder.toString();
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
