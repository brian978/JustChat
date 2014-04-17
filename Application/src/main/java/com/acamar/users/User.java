package com.acamar.users;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public class User
{
    private String identity = "";
    private String name = "";
    private UserState state = UserState.OFFLINE;

    public User(String identity)
    {
        this.identity = identity;
    }

    public User(String identity, String name)
    {
        this.identity = identity;
        this.name = name;
    }

    public void setState(UserState state)
    {
        this.state = state;
    }

    public String getIdentity()
    {
        return identity;
    }

    public String getName()
    {
        return name;
    }

    public UserState getState()
    {
        return state;
    }
}
