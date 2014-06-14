package com.acamar.authentication;

import com.acamar.event.AbstractEvent;
import com.acamar.users.User;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class AuthenticationEvent extends AbstractEvent
{
    private StatusCode statusCode;
    private User user = null;

    public AuthenticationEvent(User user, StatusCode statusCode)
    {
        this.user = user;
        this.statusCode = statusCode;
    }

    /**
     * Returns the user that tried to authenticate
     *
     * @return User
     */
    public User getUser()
    {
        return user;
    }

    /**
     * Returns the status code of the authentication
     *
     * @return StatusCode
     */
    public StatusCode getStatusCode()
    {
        return statusCode;
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
