package com.acamar.event;

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

    public static void remove(Class listenerClass, Object listener)
    {
        ArrayList<Object> listeners = EventManager.getListeners(listenerClass);
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    public static ArrayList<Object> getListeners(Class listenerClass)
    {
        ArrayList<Object> listeners = EventManager.listeners.get(listenerClass);

        return listeners == null ? new ArrayList<>() : listeners;
    }


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
