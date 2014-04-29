package com.justchat.client.gui.panel.components;

import com.acamar.users.User;
import com.acamar.users.UsersManagerListener;

import javax.swing.*;
import java.util.ArrayList;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserList extends JList<User> implements UsersManagerListener
{
    private DefaultListModel<User> dataModel;

    public UserList()
    {
        dataModel = new DefaultListModel<User>();

        setModel(dataModel);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setVisibleRowCount(-1);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void addedUser(User user)
    {
        dataModel.addElement(user);
    }

    @Override
    public void removedUser(User user)
    {
        dataModel.removeElement(user);
    }

    public void removeAllElements()
    {
        dataModel.removeAllElements();
    }

    @Override
    public void sortComplete(ArrayList<User> list)
    {
        removeAllElements();
        for (User user : list) {
            addedUser(user);
        }
    }
}
