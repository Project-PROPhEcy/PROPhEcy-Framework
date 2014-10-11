package com.prophecy.processing.processor.contexts.lineage;

import java.util.*;

/**
 * Created by alpha_000 on 04.07.2014.
 */
public class EventManager {

    //----------------------------------------
    // Class Variables
    //----------------------------------------


    private Map<Integer, Event> _events
            = new HashMap<>();

    private Map<Integer, List<Event>> _blockIds
            = new HashMap<>();


    //----------------------------------------
    // Class Properties
    //----------------------------------------


    /**
     * Gets all available block ids.
     */
    public Set<Integer> getBIDs() {
        return _blockIds.keySet();
    }

    /**
     * Gets the number of events.
     */
    public int size() {
        return _events.size();
    }


    //----------------------------------------
    // Class Functions
    //----------------------------------------


    /**
     * Gets the event with the specific
     * values or creates a new one.
     * @param bid The block id.
     * @param tid The tuple id.
     * @param prob The probability.
     * @return The event.
     */
    public Event create(int bid, int tid, Double prob) {

        int hashCode = Event
                .HashCode(bid, tid, prob);

        if (!_events.containsKey(hashCode)) {

            Event event = new Event(
                    bid, tid, prob);

            _events.put(hashCode, event);

            if(!_blockIds.containsKey(bid))
                _blockIds.put(bid, new ArrayList<>());

            _blockIds.get(bid).add(event);
        }

        return _events.get(hashCode);
    }

    /**
     * Gets the event for a specific block id.
     * @param bid The block id.
     * @return A list of events or null.
     */
    public List<Event> getAll(int bid) {
       return  _blockIds.getOrDefault(bid, null);
    }
}
