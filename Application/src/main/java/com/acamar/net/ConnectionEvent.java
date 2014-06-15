package com.acamar.net;

import com.acamar.event.AbstractEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class ConnectionEvent extends AbstractEvent
{
    public final static int UNKOWN = 0;
    public final static int CONNECTION_OPENED = 1;
    public final static int ERROR_OCCURED = 2;
    public final static int CONNECTION_CLOSED = 3;

    protected String message = "";
    protected int statusCode = UNKOWN;

    public ConnectionEvent(String message, int statusCode)
    {
        this.message = message;
        this.statusCode = statusCode;
    }

    /**
     * Returns the message for the event
     *
     * @return String
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Returns the status code so we know what to do in code
     *
     * @return int
     */
    public int getStatusCode()
    {
        return statusCode;
    }
}
