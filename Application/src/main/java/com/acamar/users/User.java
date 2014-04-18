package com.acamar.users;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public class User implements UserInterface
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

    @Override
    public void setState(UserState state)
    {
        this.state = state;
    }

    @Override
    public String getIdentity()
    {
        return identity;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public UserState getState()
    {
        return state;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static enum UserState
    {
        ONLINE,
        UNAVAILABLE,
        OFFLINE
    }
}
