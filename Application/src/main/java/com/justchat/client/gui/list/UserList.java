package com.justchat.client.gui.list;

import com.justchat.model.user.identity.User;
import com.justchat.model.user.manager.observer.UsersActionsObserver;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserList extends JList<String> implements UsersActionsObserver
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
        ((DefaultListModel<String>) getModel()).addElement(user.getUsername());
    }

    @Override
    public void removeduser(User user)
    {
        ((DefaultListModel<String>) getModel()).removeElement(user.getUsername());
    }
}
