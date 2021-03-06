package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.smack.roster.RosterAdapter;
import com.acamar.users.UsersManager;
import com.acamar.util.Properties;
import com.acamar.util.PropertiesAwareInterface;
import com.justchat.mvc.view.frame.Contacts;
import com.justchat.mvc.view.frame.Conversation;
import com.justchat.mvc.view.panel.UsersPanel;
import com.justchat.mvc.view.panel.components.UserCategory;
import com.justchat.mvc.view.panel.components.UserList;
import com.justchat.users.User;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class ContactsController extends AbstractController implements PropertiesAwareInterface
{
    private Properties settings = null;
    private Contacts view = new Contacts();
    private Authentication authentication = null;
    private UsersManager usersManager = new UsersManager();

    /**
     * The method will be called after all the dependencies have been injected in the controller
     */
    @Override
    public void completeSetup()
    {
        if (!setupCompleted) {
            view.setEventManager(eventManager);

            // Initializing the frame and selecting a default communication service
            initializeFrame();
        }
    }

    /**
     * Calls the initialize() method on the login frame and then attaches the event listeners
     */
    private void initializeFrame()
    {
        view.initialize();

        setViewPreferredSize();

        // Now that we have all the elements on the frame we need to attach some events on it
        attachEvents();
    }

    /**
     * Sets the preferred size of the view
     */
    private void setViewPreferredSize()
    {
        JFrame container = view.getViewContainer();
        Dimension preferredSize = container.getPreferredSize();
        Dimension size = container.getSize();
        Object width = settings.get("ContactsWidth", String.valueOf((int) size.getWidth()));
        Object height = settings.get("ContactsHeight", String.valueOf((int) size.getHeight()));

        if (width != null && height != null) {
            preferredSize = new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        container.setPreferredSize(preferredSize);
    }

    /**
     * Attached the events for which the controller or a nested object will listen to
     */
    private void attachEvents()
    {
        /**
         * -----------------------
         * Event manager events
         * -----------------------
         */
        eventManager.attach(AuthenticationEvent.class, new LoginEventListener());
        eventManager.attach(AuthenticationEvent.TYPE_LOGOUT, new LogoutEventListener());

        // Application exit handlers
        eventManager.attach("application.exit", new AbstractEventListener()
        {
            /**
             * The method is called by the event manager when an EventListener class is passed to the trigger() method
             *
             * @param e Event that was triggered
             */
            @Override
            public void onEvent(EventInterface e)
            {
                eventManager.trigger(new MvcEvent(MvcEvent.APPLICATION_EXIT, view));
            }
        });

        /**
         * -----------------------
         * Components events
         * -----------------------
         */
        // View events
        view.getUsersPanel().userList.addMouseListener(new MouseAdapter()
        {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    User remoteUser = ((UserList) e.getSource()).getSelectedUser();
                    if (remoteUser.getIdentity().length() > 0) {
                        HashMap<Object, Object> params = new HashMap<>();
                        params.put("remoteUser", remoteUser);
                        eventManager.trigger("new.conversation", null, params);
                    }
                }
            }
        });

        // We also have from frame events that will trigger events on the event manager
        view.getViewContainer().addWindowListener(new WindowAdapter()
        {
            /**
             * Invoked when a window has been closed.
             *
             * @param e Window event object
             */
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosed(e);
                eventManager.trigger(new MvcEvent(MvcEvent.WINDOW_CLOSING, view));
            }
        });
    }

    /**
     * Injects a properties object
     *
     * @param properties Properties to be injected
     */
    @Override
    public void setProperties(Properties properties)
    {
        settings = properties;
    }

    /**
     * @return A Properties object
     */
    @Override
    public Properties getProperties()
    {
        return settings;
    }

    /**
     * Populates the users panel, adds some listeners and populates the user list
     */
    private void prepareUserListDependencies()
    {
        UsersPanel usersPanel = view.getUsersPanel();
        usersPanel.populate();

        // Updating the listeners for the users manager
        usersManager.addListener(usersPanel.userList);

        // Updating the roster
        authentication.getConnection().getEndpoint().getRoster().addRosterListener(new PresenceListener());
    }

    /**
     * Adds the users into the user manager
     */
    private void addUsers()
    {
        Roster roster = authentication.getConnection().getEndpoint().getRoster();
        UserList userList = view.getUsersPanel().userList;
        Collection<RosterEntry> buddyList = roster.getEntries();
        User user;
        Presence presence;

        // Getting the default categories for now
        UserCategory onlineCategory = userList.findCategory("Online");

        // Default user category
        UserCategory userCategory = userList.findCategory("Offline");

        // Adding and sorting the users
        for (RosterEntry buddy : buddyList) {
            presence = roster.getPresence(buddy.getUser());

            if (presence.isAvailable()) {
                userCategory = onlineCategory;
            }

            user = new User(buddy.getUser(), buddy.getName());
            user.setCategory(userCategory);

            usersManager.add(user);
        }

        // No need to also import the users since they will be imported after the sort
        usersManager.sort();
    }

    /**
     * Does a user list cleanup and then disconnects from the XMPP server
     */
    private void doLogout()
    {
        usersManager.removeAll();
        authentication.getConnection().disconnect();
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-03
     */
    private class LoginEventListener extends AbstractEventListener
    {
        private boolean panelPrepared = false;

        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         * It will update the login fields
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            AuthenticationEvent event = (AuthenticationEvent) e;

            // If the login is successful we hide the current frame (since we don't need it for now)
            if (event.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                authentication = (Authentication) event.getParams().get("object");

                // Showing the user list
                if (!panelPrepared) {
                    panelPrepared = true;
                    prepareUserListDependencies();
                }

                addUsers();
                view.display();
            }
        }
    }

    /**
     * The class decides what happens to the frame when a logout occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-07-10
     */
    private class LogoutEventListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            Dimension size = view.getViewContainer().getSize();
            settings.set("ContactsWidth", String.valueOf((int) size.getWidth()));
            settings.set("ContactsHeight", String.valueOf((int) size.getHeight()));

            doLogout();
            view.getViewContainer().invalidate();
            view.getViewContainer().setVisible(false);
        }
    }

    /**
     * The listener will change the user category when the presence changes
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-07-07
     */
    private class PresenceListener extends RosterAdapter
    {
        @Override
        public void presenceChanged(Presence presence)
        {
            UserList userList = view.getUsersPanel().userList;

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
