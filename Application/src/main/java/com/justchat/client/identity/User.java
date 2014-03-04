package com.justchat.client.identity;

import com.justchat.client.websocket.Connection;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class User
{
    private String id = "";
    private String username = "";
    private boolean isCurrent = false;
    private Token token = null;
    private Connection connection = null;

    public User(String username)
    {
        this(username, false);
    }

    public User(String id, String username)
    {
        this(username, false);

        this.id = id;
    }

    public User(String username, boolean isCurrent)
    {
        this.username = username;
        this.isCurrent = isCurrent;
    }

    public String getUsername()
    {
        return username;
    }

    public User setConnection(Connection connection)
    {
        this.connection = connection;

        return this;
    }
}
