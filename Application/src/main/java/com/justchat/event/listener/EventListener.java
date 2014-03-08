package com.justchat.event.listener;

import com.justchat.event.EventObject;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface EventListener
{
    public <T> void handleEvent(EventObject<T> event);
}
