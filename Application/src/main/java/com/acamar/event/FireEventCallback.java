package com.acamar.event;

/**
 * JustChat
 *
 * The interface can be used in case something custom needs to be done to fire an event
 *
 * @link https://github.com/brian978/JustChat
 */
public interface FireEventCallback
{
    /**
     *
     * @param listener Object that is used to listen for an event
     * @param e Event that will be triggered
     */
    public void fireEvent(Object listener, EventInterface e);
}
