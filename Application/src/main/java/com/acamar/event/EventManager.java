package com.acamar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class EventManager
{
    private static HashMap<Class, ArrayList<Object>> listeners = new HashMap<>();

    /**
     * Adds an event listener to the queue
     *
     * @param listenerClass The class of the event listener is used to group a the event listeners
     * @param listener      The object that will listen for events
     */
    public static void add(Class listenerClass, Object listener)
    {
        ArrayList<Object> listeners = EventManager.listeners.get(listenerClass);

        // Initializing the listeners ArrayList in case it's not present
        if (listeners == null) {
            listeners = new ArrayList<>();
            EventManager.listeners.put(listenerClass, listeners);
        }

        listeners.add(listener);
    }

    /**
     * Removes an event listener to the queue
     *
     * @param listenerClass The class of the event listener is used to identify the group of listeners where to look
     * @param listener      The object that will listen for events
     */
    public static void remove(Class listenerClass, Object listener)
    {
        ArrayList<Object> listeners = EventManager.getListeners(listenerClass);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * @param listenerClass The class of the event listeners that will be returned
     * @return ArrayList<Object>
     */
    public static ArrayList<Object> getListeners(Class listenerClass)
    {
        ArrayList<Object> listeners = EventManager.listeners.get(listenerClass);

        return listeners == null ? new ArrayList<>() : listeners;
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
    public static void fireEvent(Class listenerClass, EventInterface e, Method methodToCall) throws InvocationTargetException, IllegalAccessException
    {
        ArrayList<Object> listeners = EventManager.getListeners(listenerClass);
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
    public static void fireEvent(Class listenerClass, EventInterface e, FireEventCallback fireEventCallback)
    {
        ArrayList<Object> listeners = EventManager.getListeners(listenerClass);
        if (!listeners.isEmpty()) {
            for (Object listener : listeners) {
                fireEventCallback.fireEvent(listener, e);
                if (e.isPropagationStopped()) {
                    break;
                }
            }
        }
    }
}
