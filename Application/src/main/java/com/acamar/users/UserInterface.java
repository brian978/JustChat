package com.acamar.users;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public interface UserInterface
{
    /**
     * Returns a string that is used to identify the user on the server (like email or username)
     *
     * @return String
     */
    public String getIdentity();

    /**
     * Returns the name of the user that will be visible in the UI
     *
     * @return String
     */
    public String getName();

    /**
     * Sets the state of the user (like online/offline)
     *
     * @param state State of the user
     */
    public void setState(User.UserState state);

    /**
     * Returns the state of the user (like online/offline)
     *
     * @return User.UserState
     */
    public User.UserState getState();
}
