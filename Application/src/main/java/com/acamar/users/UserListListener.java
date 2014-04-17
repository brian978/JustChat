package com.acamar.users;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface UserListListener
{
    public void addedUser(User user);

    public void removedUser(User user);
}
