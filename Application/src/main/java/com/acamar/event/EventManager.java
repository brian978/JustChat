package com.acamar.event;

import com.acamar.event.listener.EventListenerInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
    protected HashMap<Class, ArrayList<Object>> listeners = new HashMap<>();
    protected HashMap<String, ArrayList<EventListenerInterface>> events = new HashMap<>();

    /**
     * Attaches a listener to a named event
     *
     * @param name     Name of the event. This can also be the class name of an event class
     * @param listener Listener object that will handle the event
     */
    public void attach(String name, EventListenerInterface listener)
    {
        ArrayList<EventListenerInterface> listeners = getListeners(name);

        listeners.add(listener);
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
        ArrayList<EventListenerInterface> listeners = getListeners(name);
        if (listeners != null) {
            listeners.remove(listener);
        }
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
     * Triggers an event
     *
     * @param name   Name of the event to trigger
     * @param target Target of the event. This is usually the object that triggered the event
     * @param params Parameters for the event
     */
    public void trigger(String name, Object target, HashMap<Object, Object> params)
    {
        ArrayList<EventListenerInterface> listeners = this.events.get(name);
        if (listeners != null) {
            Event event = new Event(name, target, params);
            trigger(name, event);
        }
    }

    /**
     * Triggers an event
     *
     * @param name  Name of the event to be triggered
     * @param event Event to be triggered
     */
    public void trigger(String name, Event event)
    {
        ArrayList<EventListenerInterface> listeners = this.events.get(name);
        if (listeners != null) {
            for (EventListenerInterface listener : listeners) {
                listener.onEvent(event);
                if (event.isPropagationStopped()) {
                    break;
                }
            }
        }
    }

    /**
     * Triggers an event
     *
     * @param event Event to be triggered
     */
    public void trigger(Event event)
    {
        trigger(event.getClass().getName(), event);
    }

    /**
     * Triggers an event
     *
     * @param name   Name of the event to trigger
     * @param target Target of the event. This is usually the object that triggered the event
     */
    public void trigger(String name, Object target)
    {
        trigger(name, target, new HashMap<>());
    }

    /**
     * Triggers an event
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
     * Triggers an event
     *
     * @param eventClass Class that identifies the event
     * @param target     Target of the event. This is usually the object that triggered the event
     */
    public void trigger(Class eventClass, Object target)
    {
        trigger(eventClass.getName(), target, new HashMap<>());
    }

    /**
     * Returns a list of listeners that have the requested class
     *
     * @param listenerClass The class of the event listeners that will be returned
     * @return ArrayList<Object>
     */
    public ArrayList<Object> getListeners(Class listenerClass)
    {
        ArrayList<Object> listeners = this.listeners.get(listenerClass);

        return listeners == null ? new ArrayList<>() : listeners;
    }

    /**
     * Returns a list of listeners (if any) for the requested event
     *
     * @param name Name of the event to get the listeners for
     * @return ArrayList
     */
    public ArrayList<EventListenerInterface> getListeners(String name)
    {
        ArrayList<EventListenerInterface> listeners = this.events.get(name);

        return listeners == null ? new ArrayList<EventListenerInterface>() : listeners;
    }
}
