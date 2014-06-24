package com.acamar.event;

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
     * @param name Name of the event. This can also be the class name of an event class
     * @param listener Listener object that will handle the event
     */
    public void attach(String name, EventListenerInterface listener)
    {
        ArrayList<EventListenerInterface> events = this.events.get(name);

        // Initializing the events list if we don't have one for the event
        if (events == null) {
            events = new ArrayList<>();
            this.events.put(name, events);
        }

        events.add(listener);
    }

    /**
     * Attaches a listener to a named event
     *
     * @param eventClass Class that identifies the event
     * @param listener Listener object that will handle the event
     */
    public void attach(Class eventClass, EventListenerInterface listener)
    {
        attach(eventClass.getName(), listener);
    }

    /**
     * Detaches an event listener from an event
     *
     * @param name Name of the event to remove the listener from
     * @param listener The listener object to be removed
     */
    public void detach(String name, EventListenerInterface listener)
    {
        ArrayList<EventListenerInterface> listeners = this.getListeners(name);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Detaches an event listener from an event
     *
     * @param eventClass Class that identifies the event
     * @param listener The listener object to be removed
     */
    public void detach(Class eventClass, EventListenerInterface listener)
    {
        detach(eventClass.getName(), listener);
    }

    /**
     * Triggers an event
     *
     * @param name Name of the event to trigger
     * @param target Target of the event. This is usually the object that triggered the event
     * @param params Parameters for the event
     */
    public void trigger(String name, Object target, HashMap<Object, Object> params)
    {
        ArrayList<EventListenerInterface> listeners = this.events.get(name);
        if(listeners != null) {
            Event event = new Event(name, target, params);
            for(EventListenerInterface listener : listeners) {
                listener.onEvent(event);
                if(event.isPropagationStopped()) {
                    break;
                }
            }
        }
    }

    /**
     * Triggers an event
     *
     * @param name Name of the event to be triggered
     * @param event Event to be triggered
     */
    public void trigger(String name, Event event)
    {
        ArrayList<EventListenerInterface> listeners = this.events.get(name);
        if(listeners != null) {
            for(EventListenerInterface listener : listeners) {
                listener.onEvent(event);
                if(event.isPropagationStopped()) {
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
     * @param name Name of the event to trigger
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
     * @param target Target of the event. This is usually the object that triggered the event
     * @param params Parameters for the event
     */
    public void trigger(Class eventClass, Object target, HashMap<Object, Object> params)
    {
        trigger(eventClass.getName(), target, params);
    }

    /**
     * Triggers an event
     *
     * @param eventClass Class that identifies the event
     * @param target Target of the event. This is usually the object that triggered the event
     */
    public void trigger(Class eventClass, Object target)
    {
        trigger(eventClass.getName(), target, new HashMap<>());
    }

    /**
     * Adds an event listener to the queue
     *
     * @param listenerClass The class of the event listener is used to group a the event listeners
     * @param listener      The object that will listen for events
     */
    public void add(Class listenerClass, Object listener)
    {
        ArrayList<Object> listeners = this.listeners.get(listenerClass);

        // Initializing the listeners ArrayList in case it's not present
        if (listeners == null) {
            listeners = new ArrayList<>();
            this.listeners.put(listenerClass, listeners);
        }

        listeners.add(listener);
    }

    /**
     * Adds an event listener to the queue
     *
     * @param listener Object of the listener to add
     */
    public void add(EventListenerInterface listener)
    {
        add(listener.getClass(), listener);
    }

    /**
     * Removes an event listener to the queue
     *
     * @param listenerClass The class of the event listener is used to identify the group of listeners where to look
     * @param listener      The object that will listen for events
     */
    public void remove(Class listenerClass, Object listener)
    {
        ArrayList<Object> listeners = this.getListeners(listenerClass);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Removes an event listener to the queue
     *
     * @param listener Object of the listener to remove
     */
    public void remove(EventListenerInterface listener)
    {
        remove(listener.getClass(), listener);
    }

    /**
     * Dispatches an event to the registered listeners that have a certain class
     *
     * @param listenerClass The class of the event listeners that will be notified of the event
     * @param e             The event object
     * @param methodToCall  An object that identifies the method to call when dispatching the event
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void trigger(Class listenerClass, EventInterface e, Method methodToCall) throws InvocationTargetException, IllegalAccessException
    {
        ArrayList<Object> listeners = this.getListeners(listenerClass);
        if (!listeners.isEmpty()) {
            for (Object listener : listeners) {
                methodToCall.invoke(listener, e);
                if (e.isPropagationStopped()) {
                    break;
                }
            }
        }
    }

    /**
     * Dispatches an event to the registered listeners that have a certain class
     *
     * @param listenerClass     The class of the event listeners that will be notified of the event
     * @param e                 The event object
     * @param fireEventCallback An object that will dispatch the event to a method of the listener
     */
    public void trigger(Class listenerClass, EventInterface e, FireEventCallback fireEventCallback)
    {
        ArrayList<Object> listeners = this.getListeners(listenerClass);
        if (!listeners.isEmpty()) {
            for (Object listener : listeners) {
                fireEventCallback.trigger(listener, e);
                if (e.isPropagationStopped()) {
                    break;
                }
            }
        }
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
