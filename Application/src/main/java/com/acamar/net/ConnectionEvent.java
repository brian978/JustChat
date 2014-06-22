package com.acamar.net;

import com.acamar.event.Event;

import java.util.HashMap;

/**
 * JustChat
 *
 * @version 2.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-30
 */
public class ConnectionEvent extends Event
{
    /**
     * Constructs a new event object
     *
     * @param target Target of the event (usually the object that triggered the event)
     */
    public ConnectionEvent(Object target)
    {
        super("connection", target);

        // Adding a default status code
        params.put("statusCode", StatusCode.UNKOWN);
    }

    /**
     * Constructs a new event object
     *
     * @param target Target of the event (usually the object that triggered the event)
     * @param params Parameters for the event
     */
    public ConnectionEvent(Object target, HashMap<Object, Object> params)
    {
        super("connection", target, params);
    }

    /**
     * Returns the message for the event
     *
     * @return String
     */
    public String getMessage()
    {
        return (String) params.get("message");
    }

    /**
     * Returns the status code so we know what to do in code
     *
     * @return int
     */
    public StatusCode getStatusCode()
    {
        return (StatusCode) params.get("statusCode");
    }

    /**
     * Used to avoid creating constants
     *
     */
    public enum StatusCode
    {
        UNKOWN,
        CONNECTION_OPENED,
        ERROR_OCCURED,
        CONNECTION_CLOSED
    }
}
