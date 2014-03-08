package com.justchat.client.websocket;

import com.justchat.model.user.identity.User;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Message
{
    protected User user = null;
    protected String message = "";

    public Message(User user, String message)
    {
        this.user = user;
        this.message = message;
    }

    public User getUser()
    {
        return user;
    }

    public String getMessage()
    {
        return message;
    }
}
