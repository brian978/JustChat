package com.acamar.event;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
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
