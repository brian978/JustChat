package com.justchat.view.frame;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.justchat.users.User;
import com.acamar.users.UsersManager;
import com.acamar.util.Properties;
import com.justchat.view.frame.menu.MainMenu;
import com.justchat.view.panel.UserListPanel;
import com.justchat.view.panel.components.UserList;

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
    private AuthenticationListener authenticationListener = new AuthenticationStatusListener();
    private UsersManager usersManager = new UsersManager();
    private UserListPanel userListPanel = new UserListPanel(usersManager);

    public Contacts(Properties settings)
    {
        super("JustChat - Contacts", settings);
    }

    /**
     * Sets the minimum size of the frame
     */
    @Override
    protected void ensureMinimumSize()
    {
        container.setMinimumSize(new Dimension(200, 300));
    }

    /**
     * The method is used to show the frame, pack it to it's minimum size (considering the elements on it)
     * and set the location of the frame (which by default will be centered)
     *
     */
    @Override
    public void display()
    {
        // Updating the window dimensions to what the user last set
        container.setPreferredSize(getSizePreferences());

        super.display();
    }

    public Contacts addAuthenticationListeners()
    {
        xmppAuthentication.addAuthenticationListener(authenticationListener);

        return this;
    }

    public Contacts removeAuthenticationListeners()
    {
        xmppAuthentication.addAuthenticationListener(authenticationListener);

        return this;
    }

    public void updateRoster()
    {
        // The roster is needed when we do operations on the user list
        userListPanel.setRoster(xmppAuthentication.getConnection().getEndpoint().getRoster());
    }

    @Override
    protected void configure()
    {
        super.configure();

        container.setLayout(new BoxLayout(container.getContentPane(), BoxLayout.PAGE_AXIS));
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     *
     */
    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        container.setJMenuBar(menu.getContainer());

        menu.display(MainMenu.DEFAULT_ITEMS);
        menu.display(MainMenu.AUTHENTICATED_ITEMS);

        /**
         * -------------
         * User list
         * -------------
         */
        container.add(userListPanel);
    }

    /**
     * Adds event handlers to the events that will be triggered by elements on the frame
     *
     */
    @Override
    protected void setupEvents()
    {
        super.setupEvents();

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
                    User user = ((UserList) e.getSource()).getSelectedUser();
                    if(user.getIdentity().length() > 0) {
                        startNewConversation(user);
                    }
                }
            }
        });

        /**
         * -----------------------
         * Menu handlers
         * -----------------------
         */
        // Logout action
        menu.findItemByName("logoutItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                container.setVisible(false);
            }
        });
    }

    public void doLogout()
    {
        // The cleanup must be done prior to the disconnect because it depends on the connection
        // in order to work properly
        userListPanel.cleanup();

        xmppAuthentication.getConnection().disconnect();
    }

    private void startNewConversation(User user)
    {
        Conversation conversationFrame = new Conversation(xmppAuthentication.getConnection(), user);
        conversationFrame.setLocalUser((User) usersManager.getUser());
    }

    private Dimension getSizePreferences()
    {
        Dimension size = container.getSize();
        Object width = settings.get("ContactsWidth", String.valueOf((int) size.getWidth()));
        Object height = settings.get("ContactsHeight", String.valueOf((int) size.getHeight()));

        if (width != null && height != null) {
            return new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        return container.getPreferredSize();
    }

    public void loadUsers()
    {
        userListPanel.addUsers();
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                usersManager.setUser(e.getUser());
            }
        }
    }
}
