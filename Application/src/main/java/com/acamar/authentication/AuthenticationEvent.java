package com.acamar.authentication;

import com.acamar.event.Event;
import com.acamar.users.User;

import java.util.HashMap;

/**
 * JustChat
 *
 * @version 2.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-30
 */
public class AuthenticationEvent extends Event
{
    public final static String TYPE_LOGIN = "login";
    public final static String TYPE_LOGOUT = "logout";

    /**
     * Constructs a new event object
     *
     * @param target Target of the event (usually the object that triggered the event)
     */
    public AuthenticationEvent(Object target)
    {
        super(AuthenticationEvent.class.getName(), target);
    }

    /**
     * Constructs a new event object
     *
     * @param target Target of the event (usually the object that triggered the event)
     * @param params Parameters for the event
     */
    public AuthenticationEvent(Object target, HashMap<Object, Object> params)
    {
        super(AuthenticationEvent.class.getName(), target, params);
    }

    /**
     * Returns the user that tried to authenticate
     *
     * @return String
     */
    public String getIdentity()
    {
        return (String) params.get("identity");
    }

    /**
     * Returns the status code of the authentication
     *
     * @return StatusCode
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
        SUCCESS,
        FAILED,
        INVALID_DATA,
        ABORTED
    }
}
