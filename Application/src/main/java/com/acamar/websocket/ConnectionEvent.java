package com.acamar.websocket;

import com.acamar.event.AbstractEvent;

import javax.websocket.Session;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionEvent extends AbstractEvent
{
    public final static int UNKOWN = 0;
    public final static int CONNECTION_OPENED = 1;
    public final static int ERROR_OCCURED = 2;
    public final static int CONNECTION_CLOSED = 3;

    private String message = "";
    private Session session = null;
    private int statusCode = UNKOWN;

    public ConnectionEvent(String message, Session session, int statusCode)
    {
        this.message = message;
        this.session = session;
        this.statusCode = statusCode;
    }

    public String getMessage()
    {
        return message;
    }

    public Session getSession()
    {
        return session;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}
