package com.justchat.client.frame;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.gui.frame.AbstractFrame;
import com.acamar.net.ConnectionException;
import com.acamar.net.xmpp.Connection;
import com.acamar.users.User;
import com.acamar.users.UsersManager;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.ContactsMenu;
import com.justchat.client.gui.panel.UserListPanel;
import com.justchat.client.gui.panel.components.UserList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-22
 */
public class Contacts extends AbstractMainFrame
{
    private ContactsMenu menu = new ContactsMenu();
    private UsersManager usersManager = new UsersManager();

    UserListPanel userListPanel = new UserListPanel(usersManager);

    public Contacts(Properties settings)
    {
        super("JustChat - Contacts", settings);

        configureFrame();
        populateFrame();
        setupEvents();
    }

    @Override
    public void showFrame()
    {
        setMinimumSize(new Dimension(200, 300));

        // Updating the window dimensions to what the user last set
        setPreferredSize(getSizePreferences());

        super.showFrame();
    }

    public Contacts addAuthenticationListeners()
    {
        xmppAuthentication.addAuthenticationListener(new AuthenticationStatusListener());

        return this;
    }

    @Override
    protected void configureFrame()
    {
        super.configureFrame();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        setJMenuBar(menu);

        /**
         * -------------
         * User list
         * -------------
         */
        add(userListPanel);
    }

    public void loadUsers()
    {
        userListPanel.addUsers(xmppConnection.getEndpoint().getRoster().getEntries());
    }

    private void setupEvents()
    {
        /**
         * -----------------------
         * Contacts list handlers
         * -----------------------
         */
        userListPanel.addMouseListener(new MouseAdapter()
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
                    startNewConversation(((UserList) e.getSource()).getSelectedValue());
                }
            }
        });

        /**
         * -----------------------
         * Menu handlers
         * -----------------------
         */
        // Exit action
        JMenuItem item = menu.findItemByName("exitItem");
        if (item != null) {
            item.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    triggerClosingEvent();
                }
            });
        }

        // Logout action
        menu.findItemByName("logoutItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });
    }

    public void doLogout()
    {
        try {
            xmppConnection.disconnect();
        } catch (ConnectionException e1) {
            e1.printStackTrace();
        }

        usersManager.removeAll();
    }

    private void startNewConversation(User user)
    {
        Conversation conversationFrame = new Conversation(xmppConnection, user);
        conversationFrame.setLocalUser(usersManager.getCurrentUser());
    }

    private Dimension getSizePreferences()
    {
        Dimension size = getSize();
        Object width = settings.get("ContactsWidth",  String.valueOf((int) size.getWidth()));
        Object height = settings.get("ContactsHeight", String.valueOf((int) size.getHeight()));

        if (width != null && height != null) {
            return new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        return getPreferredSize();
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.isAuthenticated()) {
                usersManager.setCurrentUser(e.getUser());
            }
        }
    }
}
