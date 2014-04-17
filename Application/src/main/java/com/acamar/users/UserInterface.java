package com.acamar.users;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public interface UserInterface
{
    public void setState(User.UserState state);

    public String getIdentity();

    public String getName();

    public User.UserState getState();
}
