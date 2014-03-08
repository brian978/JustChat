package com.justchat.event;

import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class EventObject<T> implements  EventInterface<T>
{
    private HashMap<String, Object> parameters;
    private T type;
    private Object source;
    private boolean isPropagationStopped = false;

    public EventObject(T type, Object source)
    {
        this.type = type;
        this.source = source;
        this.parameters = new HashMap<>();
    }

    public EventObject(T type, Object source, HashMap<String, Object> parameters)
    {
        this.type = type;
        this.source = source;
        this.parameters = parameters;
    }

    public void setParameters(HashMap<String, Object> parameters)
    {
        this.parameters = parameters;
    }

    public HashMap<String, Object> getParameters()
    {
        return parameters;
    }

    public T getType()
    {
        return type;
    }

    public Object getSource()
    {
        return source;
    }

    public void stopPropagation()
    {
        isPropagationStopped = true;
    }

    public boolean isPropagationStopped()
    {
        return isPropagationStopped;
    }
}
