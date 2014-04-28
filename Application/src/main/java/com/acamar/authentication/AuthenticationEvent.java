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
     * Properties
     * -----------------
     */
    private int statusCode = 0;
    private User user = null;

    public AuthenticationEvent(User user, int statusCode)
    {
        this.user = user;
        this.statusCode = statusCode;
    }

    public User getUser()
    {
        return user;
    }

    public int getStatusCode()
    {
        return statusCode;
    }
}
