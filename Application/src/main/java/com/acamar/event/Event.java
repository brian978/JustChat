package com.acamar.event;

import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-03
 */
public class Event implements EventInterface
{
    protected String name;
    protected Object target;
    protected HashMap<Object, Object> params = new HashMap<>();
    protected boolean propagating = true;

    /**
     * Constructs a new event object
     *
     * @param name   Name of the event
     * @param target Target of the event (usually the object that triggered the event)
     */
    public Event(String name, Object target)
    {
        this.name = name;
        this.target = target;
    }

    /**
     * Constructs a new event object
     *
     * @param name   Name of the event
     * @param target Target of the event (usually the object that triggered the event)
     * @param params Parameters for the event
     */
    public Event(String name, Object target, HashMap<Object, Object> params)
    {
        this.name = name;
        this.target = target;
        this.params = params;
    }

    /**
     * Returns the name of the event
     *
     * @return String
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Returns the target object of the event
     *
     * @return Object
     */
    @Override
    public Object getTarget()
    {
        return target;
    }

    /**
     * Returns the parameters hash map the the event contains
     *
     * @return HashMap
     */
    @Override
    public HashMap<Object, Object> getParams()
    {
        return params;
    }

    /**
     * When called a flag is set that will prevent the event to be dispatched to the remaining listeners
     */
    @Override
    public void stopPropagation()
    {
        propagating = false;
    }

    /**
     * When dispatching an event this is used to check if the dispatch should be stopped
     *
     * @return boolean
     */
    @Override
    public boolean isPropagationStopped()
    {
        return !propagating;
    }
}
