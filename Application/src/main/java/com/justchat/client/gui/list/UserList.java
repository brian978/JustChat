package com.justchat.client.gui.list;

import com.acamar.users.User;
import com.acamar.users.UserListListener;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserList extends JList<String> implements UserListListener
{
    public UserList()
    {
        setModel(new DefaultListModel<String>());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setVisibleRowCount(-1);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void addedUser(User user)
    {
        ((DefaultListModel<String>) getModel()).addElement(user.getName());
    }

    @Override
    public void removedUser(User user)
    {
        ((DefaultListModel<String>) getModel()).removeElement(user.getName());
    }
}
