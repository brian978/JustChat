package com.justchat.client.gui.panel.components;

import com.acamar.users.User;
import com.acamar.users.UsersManagerListener;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserList extends JList<User> implements UsersManagerListener
{
    public UserList()
    {
        setModel(new DefaultListModel<User>());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setVisibleRowCount(-1);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void addedUser(User user)
    {
        ((DefaultListModel<User>) getModel()).addElement(user);
    }

    @Override
    public void removedUser(User user)
    {
        ((DefaultListModel<User>) getModel()).removeElement(user);
    }
}
