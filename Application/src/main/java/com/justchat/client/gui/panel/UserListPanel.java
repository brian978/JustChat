package com.justchat.client.gui.panel;

import com.acamar.gui.swing.panel.AbstractPanel;
import com.acamar.users.User;
import com.acamar.users.UsersManager;
import com.justchat.client.gui.panel.components.UserCategory;
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
        JScrollPane scrollableUserList = new JScrollPane(userList);

        // Adding the UserList to the panel
        add(scrollableUserList);
    }

    public void addUsers(Collection<RosterEntry> buddyList)
    {
        User user;

        // Getting the default categories for now
        UserCategory onlineCategory = userList.findCategory("Online");

        // TODO: optimize this so we don't add the list of users to the user list twice
        for (RosterEntry buddy : buddyList) {
            user = new User(buddy.getUser(), buddy.getName());
            user.setCategory(onlineCategory);

            usersManager.add(user);
        }

        usersManager.sort();
    }

    public void addMouseListener(MouseListener mouseListener)
    {
        userList.addMouseListener(mouseListener);
    }
}
