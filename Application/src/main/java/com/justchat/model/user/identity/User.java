package com.justchat.model.user.identity;

import com.acamar.websocket.Connection;

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

    public User(String id, String username, Token token, Connection connection)
    {
        this.id = id;
        this.username = username;
        this.token = token;
        this.connection = connection;
    }

    public User(String id, String username, boolean isCurrent)
    {
        this.id = id;
        this.username = username;
        this.isCurrent = isCurrent;
    }

    public User(String id, String username)
    {
        this(id, username, false);
    }

    public User setConnection(Connection connection)
    {
        this.connection = connection;

        return this;
    }

    public String getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean isCurrent()
    {
        return isCurrent;
    }

    public Token getToken()
    {
        return token;
    }

    public Connection getConnection()
    {
        return connection;
    }
}
