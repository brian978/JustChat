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
    /**
     * -----------------
     * Status codes
     * -----------------
     */
    public static final int SUCCESS = 200;
    public static final int FAILED = 300;
    public static final int INVALID_DATA = 310;
    public static final int ABORTED = 400;

    /**
     * -----------------
     * Properties
     * -----------------
     */
    private boolean authenticated = false;
    private int statusCode = 0;
    private User user = null;

    public AuthenticationEvent(User user, boolean authenticated, int statusCode)
    {
        this.user = user;
        this.authenticated = authenticated;
        this.statusCode = statusCode;
    }

    public User getUser()
    {
        return user;
    }

    public boolean isAuthenticated()
    {
        return authenticated;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}
