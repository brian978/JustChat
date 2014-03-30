package com.acamar.event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class EventManager
{
    private static HashMap<Object, ArrayList<Object>> listeners = new HashMap<>();

    public static void add(Object listenerClass, Object listener)
    {
        ArrayList<Object> listeners = EventManager.listeners.get(listenerClass);

        // Initializing the listeners ArrayList in case it's not present
        if (listeners == null) {
            listeners = new ArrayList<>();
            EventManager.listeners.put(listenerClass, listeners);
        }

        listeners.add(listener);
    }

    public static ArrayList<Object> getListeners(Object listenerClass)
    {
        ArrayList<Object> listeners = EventManager.listeners.get(listenerClass);

        return listeners == null ? new ArrayList<>() : listeners;
    }


    public static void fireEvent(Object listenerClass, EventInterface e, FireEventCallback fireEventCallback)
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
