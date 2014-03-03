package com.justchat.client.identity;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class User
{
    protected String id = "";
    protected String username = "";
    protected boolean isCurrent = false;

    public User(String id, String username)
    {
        this(username, false);

        this.id = id;
    }

    public User(String username)
    {
        this(username, false);
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
}
