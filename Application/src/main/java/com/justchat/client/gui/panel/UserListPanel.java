package com.justchat.client.gui.panel;

import com.acamar.gui.panel.AbstractPanel;
import com.acamar.users.User;
import com.acamar.users.UsersManager;
import com.justchat.client.gui.panel.components.UserList;
import org.jivesoftware.smack.RosterEntry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Collection;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class UserListPanel extends AbstractPanel
{
    UserList userList = new UserList();
    UsersManager usersManager = null;

    public UserListPanel(UsersManager usersManager)
    {
        super();

        this.usersManager = usersManager;
        this.usersManager.addListener(userList);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 500));

        populate();
    }

    protected void populate()
    {
        // Adding a scroll panel to the list
        JScrollPane userListScroller = new JScrollPane(userList);

        // Adding the UserList to the panel
        add(userListScroller);
    }

    public void addUsers(Collection<RosterEntry> buddyList)
    {
        for (RosterEntry buddy : buddyList) {
            usersManager.add(new User(buddy.getUser(), buddy.getName()));
        }

        usersManager.sort();
    }

    public void addMouseListener(MouseListener mouseListener)
    {
        userList.addMouseListener(mouseListener);
    }
}
