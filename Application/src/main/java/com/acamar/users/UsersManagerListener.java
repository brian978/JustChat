package com.acamar.users;

import java.util.ArrayList;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface UsersManagerListener
{
    public void addedUser(User user);

    public void removedUser(User user);

    public void sortComplete(ArrayList<User> list);
}
