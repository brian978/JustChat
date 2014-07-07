package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.util.Properties;
import com.acamar.util.PropertiesAwareInterface;
import com.justchat.mvc.view.frame.Contacts;
import com.justchat.mvc.view.frame.Conversation;
import com.justchat.mvc.view.panel.UsersPanel;
import com.justchat.mvc.view.panel.components.UserList;
import com.justchat.users.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private ArrayList<Conversation> conversations = new ArrayList<>();
    private Authentication authentication = null;

    /**
     * The method will be called after all the dependencies have been injected in the controller
     */
    @Override
    public void completeSetup()
    {
        if (!setupCompleted) {
            view.setEventManager(eventManager);
            view.getUsersPanel()
                .setEventManager(eventManager);

            setViewPreferredSize();
            attachEvents();
        }
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
        eventManager.attach(AuthenticationEvent.class, new AuthenticationEventsListener());
        eventManager.attach(AuthenticationEvent.TYPE_LOGIN, new AuthenticationEventsListener());

        // View events
        view.getUsersPanel().addMouseListener(new MouseAdapter()
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
                    User user = ((UserList) e.getSource()).getSelectedUser();
                    if (user.getIdentity().length() > 0) {
                        startNewConversation(user);
                    }
                }
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
     * When this is called a new window will be launched that will allow the users to send instant messages to the
     * selected user
     *
     * @param user User to start the conversation with
     */
    private void startNewConversation(User user)
    {
        Conversation conversationFrame = new Conversation(authentication.getConnection(), user);
//        conversationFrame.setLocalUser((User) usersManager.getUser());

        // Tracking the conversation frame
        conversations.add(conversationFrame);
    }

    private void displayUserList()
    {
        UsersPanel usersPanel = view.getUsersPanel();
        usersPanel.setRoster(authentication.getConnection().getEndpoint().getRoster());
        usersPanel.addUsers();
    }

    /**
     * Does a user list cleanup and then disconnects from the XMPP server
     */
    private void doLogout()
    {
        view.getUsersPanel().cleanup();
        authentication.getConnection().disconnect();
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-03
     */
    private class AuthenticationEventsListener extends AbstractEventListener
    {
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
            if (event.getName().equals(AuthenticationEvent.class.toString()) && event.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                authentication = (Authentication) event.getParams().get("object");

                displayUserList();
                view.display();
            }
        }
    }
}
