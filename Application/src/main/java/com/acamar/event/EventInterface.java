package com.acamar.event;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public interface EventInterface
{
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
