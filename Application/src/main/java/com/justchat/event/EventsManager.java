package com.justchat.event;

import com.justchat.event.listener.EventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class EventsManager
{
    HashMap<String, ArrayList<EventListener>> events = new HashMap<>();

    /**
     * The method returns the listeners list for a specific event and can create the list
     * if it does not exist
     */
    private ArrayList<EventListener> getListeners(String event)
    {
        // Creating the listeners list object if it doesn't exit
        if (!events.containsKey(event)) {
            events.put(event, new ArrayList<EventListener>());
        }

        // Adding the listener
        return events.get(event);
    }

    private <T> void callListeners(String event, EventObject<T> eventObject)
    {
        for (EventListener listener : events.get(event)) {
            if (eventObject.isPropagationStopped()) {
                break;
            }

            // Calling the listener
            listener.handleEvent(eventObject);
        }
    }

    public EventsManager attach(String event, EventListener listener)
    {
        getListeners(event).add(listener);

        return this;
    }

    public EventsManager trigger(String event, Object source)
    {
        if (events.containsKey(event)) {
            callListeners(event, new EventObject<>((Object) event, source));
        }

        return this;
    }

    public EventsManager trigger(String event, Object source, HashMap<String, Object> parameters)
    {
        if (events.containsKey(event)) {
            callListeners(event, new EventObject<>((Object) event, source, parameters));
        }

        return this;
    }
}
