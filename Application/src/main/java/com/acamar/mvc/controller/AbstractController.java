package com.acamar.mvc.controller;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public abstract class AbstractController implements EventManagerAwareInterface
{
    protected EventManager eventManager = null;

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager()
    {
        return eventManager;
    }
}
