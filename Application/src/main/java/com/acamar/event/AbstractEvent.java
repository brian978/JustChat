package com.acamar.event;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractEvent implements EventInterface
{
    private boolean propagating = true;

    @Override
    public void stopPropagation()
    {
        propagating = false;
    }

    @Override
    public boolean isPropagationStopped()
    {
        return !propagating;
    }
}
