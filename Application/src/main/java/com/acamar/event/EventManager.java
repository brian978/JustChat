package com.acamar.event;

import com.acamar.event.listener.EventListenerInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 2.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-30
 */
public class EventManager
{
    protected HashMap<String, ArrayList<EventListenerInterface>> events = new HashMap<>();

    /**
     * Attaches a listener to a named event
     *
     * @param name     Name of the event. This can also be the class name of an event class
     * @param listener Listener object that will handle the event
     */
    public void attach(String name, EventListenerInterface listener)
    {
        getListeners(name).add(listener);
    }

    /**
     * Attaches a listener with a priority to a named event
     *
     * @param name     Name of the event. This can also be the class name of an event class
     * @param listener Listener object that will handle the event
     * @param priority The priority that the listener will be called at
     */
    public void attach(String name, EventListenerInterface listener, Integer priority)
    {
        attach(name, listener.setPriority(priority));
    }

    /**
     * Attaches a listener to a named event
     *
     * @param eventClass Class that identifies the event
     * @param listener   Listener object that will handle the event
     * @param priority   The priority that the listener will be called at
     */
    public void attach(Class eventClass, EventListenerInterface listener, Integer priority)
    {
        attach(eventClass.getName(), listener.setPriority(priority));
    }

    /**
     * Attaches a listener to a named event
     *
     * @param eventClass Class that identifies the event
     * @param listener   Listener object that will handle the event
     */
    public void attach(Class eventClass, EventListenerInterface listener)
    {
        attach(eventClass.getName(), listener);
    }

    /**
     * Detaches an event listener from an event
     *
     * @param name     Name of the event to remove the listener from
     * @param listener The listener object to be removed
     */
    public void detach(String name, EventListenerInterface listener)
    {
        getListeners(name).remove(listener);
    }

    /**
     * Detaches an event listener from an event
     *
     * @param eventClass Class that identifies the event
     * @param listener   The listener object to be removed
     */
    public void detach(Class eventClass, EventListenerInterface listener)
    {
        detach(eventClass.getName(), listener);
    }

    /**
     * Triggers an event by name
     *
     * @param name Name of the event to be triggered
     */
    public void trigger(String name)
    {
        trigger(name, new Event(name, null));
    }

    /**
     * Triggers an event by name
     *
     * @param name  Name of the event to be triggered
     * @param event Event to be triggered
     */
    public void trigger(String name, Event event)
    {
        ArrayList<EventListenerInterface> listeners = sortListeners(getListeners(name));
        for (EventListenerInterface listener : listeners) {
            listener.onEvent(event);
            if (event.isPropagationStopped()) {
                break;
            }
        }
    }

    /**
     * Triggers the provided event
     *
     * @param event Event to be triggered
     */
    public void trigger(Event event)
    {
        trigger(event.getName(), event);
    }

    /**
     * Triggers an event
     *
     * @param name   Name of the event to trigger
     * @param target Target of the event. This is usually the object that triggered the event
     * @param params Parameters for the event
     */
    public void trigger(String name, Object target, HashMap<Object, Object> params)
    {
        trigger(name, new Event(name, target, params));
    }

    /**
     * Triggers an event using the provided name
     *
     * @param name   Name of the event to trigger
     * @param target Target of the event. This is usually the object that triggered the event
     */
    public void trigger(String name, Object target)
    {
        trigger(name, target, new HashMap<>());
    }

    /**
     * Triggers an event using the class name of the event as the event name
     *
     * @param eventClass Class that identifies the event
     * @param target     Target of the event. This is usually the object that triggered the event
     * @param params     Parameters for the event
     */
    public void trigger(Class eventClass, Object target, HashMap<Object, Object> params)
    {
        trigger(eventClass.getName(), target, params);
    }

    /**
     * Triggers an event using the class name of the event as the event name (default parameters)
     *
     * @param eventClass Class that identifies the event
     * @param target     Target of the event. This is usually the object that triggered the event
     */
    public void trigger(Class eventClass, Object target)
    {
        trigger(eventClass.getName(), target, new HashMap<>());
    }

    /**
     * Returns a list of listeners (if any) for the requested event
     *
     * @param name Name of the event to get the listeners for
     * @return ArrayList
     */
    public ArrayList<EventListenerInterface> getListeners(String name)
    {
        ArrayList<EventListenerInterface> listeners = events.get(name);
        if (listeners == null) {
            listeners = new ArrayList<>();
            events.put(name, listeners);
        }

        return listeners;
    }

    /**
     * The method clears all the events and listeners
     */
    public void clear()
    {
        events.clear();
    }

    /**
     * The method will sort the listeners by priority (higher priority first)
     *
     * @param listeners Listeners to be sorted
     */
    protected ArrayList<EventListenerInterface> sortListeners(ArrayList<EventListenerInterface> listeners)
    {
        Collections.sort(listeners, new Comparator<EventListenerInterface>()
        {
            public int compare(EventListenerInterface o1, EventListenerInterface o2)
            {
                if (o1.getPriority() < o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() > o2.getPriority()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        return listeners;
    }
}
