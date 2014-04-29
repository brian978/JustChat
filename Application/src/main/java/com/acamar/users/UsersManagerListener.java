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
    public void addedUser(User user) throws NullPointerException;

    public void removedUser(User user);

    public void sortComplete(ArrayList<User> list);
}
