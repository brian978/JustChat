package com.justchat.mvc.view.panel;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.mvc.view.AbstractPanel;
import com.acamar.smack.roster.RosterAdapter;
import com.acamar.users.UsersManager;
import com.justchat.mvc.view.panel.components.UserCategory;
import com.justchat.mvc.view.panel.components.UserList;
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
public class UsersPanel extends AbstractPanel implements EventManagerAwareInterface
{
    public final UserList userList = new UserList();

    private EventManager eventManager = null;

    public UsersPanel()
    {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(300, 500));
    }

    /**
     * Populates the panel with it's objects
     *
     */
    public void populate()
    {
        // Adding a scroll panel to the list
        JScrollPane scrollableUserList = new JScrollPane(userList);

        // Adding the UserList to the panel
        add(scrollableUserList);
    }

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager()
    {
        return eventManager;
    }
}
