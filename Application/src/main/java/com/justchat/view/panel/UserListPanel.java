package com.justchat.view.panel;

import com.acamar.mvc.view.panel.AbstractPanel;
import com.acamar.smack.roster.RosterAdapter;
import com.acamar.users.UsersManager;
import com.justchat.view.panel.components.UserCategory;
import com.justchat.view.panel.components.UserList;
import com.justchat.users.User;
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
 * @version 1.5
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-03
 */
public class UserListPanel extends AbstractPanel
{
    private UserList userList = new UserList();
    private UsersManager usersManager = null;
    private Roster roster = null;
    private RosterListener rosterListener = new PresenceListener();

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

    public void setRoster(Roster roster)
    {
        // Cleaning up the last roster first
        if (this.roster != null) {
            this.roster.removeRosterListener(rosterListener);
        }

        this.roster = roster;

        this.roster.addRosterListener(rosterListener);
    }

    public void addUsers()
    {
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

    public void cleanup()
    {
        roster.removeRosterListener(rosterListener);
        usersManager.removeAll();

        roster = null;
    }

    /**
     * The listener will change the user category when the presence changes
     */
    private class PresenceListener extends RosterAdapter
    {
        @Override
        public void presenceChanged(Presence presence)
        {
            // We need to remove the resource from the "from" string
            String from = presence.getFrom();
            int lastIndex = from.lastIndexOf('/');

            if (lastIndex >= 0) {
                from = presence.getFrom().substring(0, lastIndex);
            }

            User user = (User) usersManager.find(from);

            System.out.println(user + " changed presence to: " + presence.getType());

            if (user != null) {
                if (presence.isAvailable()) {
                    userList.updateUser(user, userList.findCategory("Online"));
                } else {
                    userList.updateUser(user, userList.findCategory("Offline"));
                }
            }
        }
    }
}
