package com.acamar.event.listener;

import com.acamar.event.EventInterface;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-24
 */
public interface EventListenerInterface
{
    /**
     *
     * @param priority The priority that the event listener will have once an event is dispatched
     */
    public EventListenerInterface setPriority(Integer priority);

    /**
     *
     * @return The priority of the listener the will be used to order the listener on an event dispatch
     */
    public Integer getPriority();

    /**
     * The method is called by the event manager when an EventListener class is passed to the trigger() method
     *
     * @param e Event that was triggered
     */
    public void onEvent(EventInterface e);
}
