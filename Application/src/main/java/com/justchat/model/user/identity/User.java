package com.justchat.model.user.identity;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class User
{
    private String id = "";
    private String username = "";
    private boolean isCurrent = false;

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
}
