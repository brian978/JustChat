package com.acamar.event;

import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-03
 */
public interface EventInterface
{
    /**
     * Returns the name of the event
     *
     * @return String
     */
    public String getName();

    /**
     * Returns the target object of the event
     *
     * @return Object
     */
    public Object getTarget();

    /**
     * Returns the parameters hash map the the event contains
     *
     * @return HashMap
     */
    public HashMap<Object, Object> getParams();

    /**
     * When called a flag is set that will prevent the event to be dispatched to the remaining listeners
     *
     */
    public void stopPropagation();

    /**
     * When dispatching an event this is used to check if the dispatch should be stopped
     *
     * @return boolean
     */
    public boolean isPropagationStopped();
}
