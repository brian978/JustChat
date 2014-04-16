package com.acamar.net.websocket;

import com.acamar.event.AbstractEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class MessageEvent extends AbstractEvent
{
    private String message;

    public MessageEvent(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
