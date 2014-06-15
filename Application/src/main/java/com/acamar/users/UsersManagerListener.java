package com.acamar.users;

import com.justchat.client.gui.panel.components.UserCategory;

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
    /**
     * Event that is triggered when a user is added in the UsersManager object
     *
     * @param user User that was added
     */
    public void userAdded(User user);

    /**
     * Event that is triggered when a user will be removed from the UsersManager object
     *
     * @param user User that will be removed
     */
    public void userRemoved(User user);

    /**
     * Event that is triggered after the user list is sorted by the UsersManager
     *
     * @param list A sorted list of users
     */
    public void usersSorted(ArrayList<User> list);
}
