package com.acamar.event;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface EventInterface
{
    public void stopPropagation();

    public boolean isPropagationStopped();
}
