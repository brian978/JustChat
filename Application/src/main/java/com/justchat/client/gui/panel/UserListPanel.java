package com.justchat.client.gui.panel;

import com.acamar.gui.panel.AbstractPanel;
import com.justchat.client.gui.list.UserList;
import com.justchat.model.user.identity.User;
import com.justchat.model.user.manager.Users;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserListPanel extends AbstractPanel
{
    UserList userList;
    Users users;

    public UserListPanel(Users users)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 500));

        this.users = users;

        populate();
    }

    protected void populate()
    {
        // Creating the UserList object so we have something to display
        userList = new UserList();

        // Adding a scroll panel to the list
        JScrollPane userListScroller = new JScrollPane(userList);

        // Adding the UserList to the panel
        add(userListScroller);

        // Setting up some dependencies
        users.addObserver(userList);

        // Adding some dummy users
        User usr;
        for (int i = 0; i < 50; i++) {
            usr = new User(Integer.toString(i), Double.toString(Math.random()) + "--" + Integer.toString(i));
            users.add(usr);
        }
    }

    public UserList getUserList()
    {
        return userList;
    }
}
