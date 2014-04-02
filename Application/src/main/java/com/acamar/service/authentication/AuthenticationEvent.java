package com.acamar.service.authentication;

import com.acamar.event.AbstractEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class AuthenticationEvent extends AbstractEvent
{
    private boolean authenticated = false;
    private int statusCode = 0;
    private String message = "";

    public AuthenticationEvent(boolean authenticated, int statusCode)
    {
        this.authenticated = authenticated;
        this.statusCode = statusCode;
    }

    public AuthenticationEvent(boolean authenticated, int statusCode, String message)
    {
        this(authenticated, statusCode);
        this.message = message;
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