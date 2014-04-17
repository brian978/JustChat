package com.justchat.client.gui.panel;

import com.acamar.gui.panel.AbstractPanel;
import com.justchat.client.gui.list.UserList;

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

    public UserListPanel()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 500));

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
    }

    public UserList getUserList()
    {
        return userList;
    }
}
