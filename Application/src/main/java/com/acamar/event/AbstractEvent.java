package com.acamar.event;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractEvent implements EventInterface
{
    private boolean propagating = true;

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
