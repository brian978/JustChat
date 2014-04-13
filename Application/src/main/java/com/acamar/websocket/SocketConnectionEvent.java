package com.acamar.websocket;

import com.acamar.event.AbstractEvent;

import javax.websocket.Session;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class SocketConnectionEvent extends com.acamar.net.ConnectionEvent
{
    private Session session = null;

    public SocketConnectionEvent(String message, Session session, int statusCode)
    {
        super(message, statusCode);

        this.session = session;
    }

    public Session getSession()
    {
        return session;
    }

}
