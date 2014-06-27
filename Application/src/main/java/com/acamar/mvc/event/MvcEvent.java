package com.acamar.mvc.event;

import com.acamar.event.Event;

import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-27
 */
public class MvcEvent extends Event
{
    public final static String WINDOW_CLOSING = "windowClosing";

    /**
     * Constructs a new event object
     *
     * @param name   Name of the event
     * @param target Target of the event (usually the object that triggered the event)
     */
    public MvcEvent(String name, Object target)
    {
        super(name, target);
    }

    /**
     * Constructs a new event object
     *
     * @param name   Name of the event
     * @param target Target of the event (usually the object that triggered the event)
     * @param params Parameters for the event
     */
    public MvcEvent(String name, Object target, HashMap<Object, Object> params)
    {
        super(name, target, params);
    }
}
