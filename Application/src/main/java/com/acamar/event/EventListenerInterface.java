package com.acamar.event;

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
     * The method is called by the event manager when an EventListener class is passed to the trigger() method
     *
     * @param e Event that was triggered
     */
    public void onEvent(EventInterface e);
}
