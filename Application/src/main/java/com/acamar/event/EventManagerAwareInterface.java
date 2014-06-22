package com.acamar.event;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public interface EventManagerAwareInterface
{
    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    public void setEventManager(EventManager eventManager);

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    public EventManager getEventManager();
}
