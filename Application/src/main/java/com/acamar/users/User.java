package com.acamar.users;

import java.text.Collator;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public class User implements UserInterface, Comparable<User>
{
    private String identity = "";
    private String name = "";
    private UserState state = UserState.OFFLINE;

    /**
     * Creates a user object only with an identity
     *
     * @param identity String the identifies the user uniquely
     */
    public User(String identity)
    {
        this.identity = identity;
    }

    /**
     * Creates a user object with both an identity and a name
     *
     * @param identity String the identifies the user uniquely
     * @param name     Name of the user that will be displayed in the GUI
     */
    public User(String identity, String name)
    {
        this(identity);

        this.name = name;
    }

    /**
     * Returns a string that is used to identify the user on the server (like email or username)
     *
     * @return String
     */
    @Override
    public String getIdentity()
    {
        return identity;
    }

    /**
     * Returns the name of the user that will be visible in the UI
     *
     * @return String
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Sets the state of the user (like online/offline)
     *
     * @param state State of the user
     */
    @Override
    public void setState(UserState state)
    {
        this.state = state;
    }

    /**
     * Returns the state of the user (like online/offline)
     *
     * @return User.UserState
     */
    @Override
    public UserState getState()
    {
        return state;
    }

    /**
     * The method is required so that in a list we need to be able to show the username
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return name;
    }

    /**
     * The method is used when sorting the users by name
     *
     * @param o user
     * @return int
     */
    @Override
    public int compareTo(User o)
    {
        return Collator.getInstance().compare(getName(), o.getName());
    }

    /**
     * Contains the types of user states
     */
    public static enum UserState
    {
        ONLINE,
        UNAVAILABLE,
        OFFLINE
    }
}
