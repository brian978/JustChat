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
    private boolean authenticated = false;
    private int statusCode = 0;
    private String message = "";
    private User user = null;

    public AuthenticationEvent(User user, boolean authenticated, int statusCode)
    {
        this.user = user;
        this.authenticated = authenticated;
        this.statusCode = statusCode;
    }

    public AuthenticationEvent(User user, boolean authenticated, int statusCode, String message)
    {
        this(user, authenticated, statusCode);
        this.message = message;
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

    public String getMessage()
    {
        return message;
    }
}
