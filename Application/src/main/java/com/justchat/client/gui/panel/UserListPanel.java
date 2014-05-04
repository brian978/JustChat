package com.justchat.client.gui.panel;

import com.acamar.gui.swing.panel.AbstractPanel;
import com.acamar.users.User;
import com.acamar.users.UsersManager;
import com.justchat.client.gui.panel.components.UserCategory;
import com.justchat.client.gui.panel.components.UserList;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;

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

    public void addUsers(Roster roster)
    {
        /**
         * -----------------------
         * XMPP Presence change
         * -----------------------
         */
        roster.addRosterListener(new RosterListener()
        {
            @Override
            public void entriesAdded(Collection<String> strings)
            {

            }

            @Override
            public void entriesUpdated(Collection<String> strings)
            {

            }

            @Override
            public void entriesDeleted(Collection<String> strings)
            {

            }

            @Override
            public void presenceChanged(Presence presence)
            {
                // We need to remove the resource from the "from" string
                String from = presence.getFrom();
                int lastIndex = from.lastIndexOf('/');

                if(lastIndex >= 0) {
                    from = presence.getFrom().substring(0, lastIndex);
                }

                User user = usersManager.find(from);

                System.out.println(user + " changed presence to: " + presence.getType());

                if (user != null) {
                    if (presence.isAvailable()) {
                        userList.updateUser(user, userList.findCategory("Online"));
                    } else {
                        userList.updateUser(user, userList.findCategory("Offline"));
                    }
                }
            }
        });

        /**
         * -----------------------
         * Adding the users
         * -----------------------
         */
        Collection<RosterEntry> buddyList = roster.getEntries();
        User user;
        Presence presence;
        UserCategory category;

        // Getting the default categories for now
        UserCategory onlineCategory = userList.findCategory("Online");

        // Default user category
        category = userList.findCategory("Offline");

        // Adding and sorting the users
        for (RosterEntry buddy : buddyList) {
            presence = roster.getPresence(buddy.getUser());

            if (presence.isAvailable()) {
                category = onlineCategory;
            }

            user = new User(buddy.getUser(), buddy.getName());
            user.setCategory(category);

            usersManager.add(user);
        }

        // No need to also import the users since they will be imported after the sort
        usersManager.sort();
    }

    public void addMouseListener(MouseListener mouseListener)
    {
        userList.addMouseListener(mouseListener);
    }
}
