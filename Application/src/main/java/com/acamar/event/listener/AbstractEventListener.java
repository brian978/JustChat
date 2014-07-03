package com.acamar.event.listener;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-07-03
 */
public abstract class AbstractEventListener implements EventListenerInterface
{
    private Integer priority = 1;

    /**
     * @param priority The priority that the event listener will have once an event is dispatched
     */
    @Override
    public AbstractEventListener setPriority(Integer priority)
    {
        this.priority = priority;

        return this;
    }

    /**
     * @return The priority of the listener the will be used to order the listener on an event dispatch
     */
    @Override
    public Integer getPriority()
    {
        return priority;
    }
}
