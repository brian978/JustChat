package com.justchat.mvc.view.frame;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.justchat.mvc.view.panel.UsersPanel;
import com.justchat.users.User;
import com.justchat.mvc.view.frame.menu.MainMenu;
import com.justchat.mvc.view.panel.components.UserList;

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
    private UsersPanel usersPanel = new UsersPanel();

    /**
     * Creates a contacts frame object
     *
     */
    public Contacts()
    {
        super("JustChat - Contacts");
    }

    /**
     *
     * @return An instance of an user list panel
     */
    public UsersPanel getUsersPanel()
    {
        return usersPanel;
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     *
     */
    @Override
    protected void configure()
    {
        super.configure();

        container.setLayout(new BoxLayout(container.getContentPane(), BoxLayout.PAGE_AXIS));
    }

    /**
     * Sets the minimum size of the frame
     *
     */
    @Override
    protected void ensureMinimumSize()
    {
        container.setMinimumSize(new Dimension(200, 300));
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     *
     */
    @Override
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
        container.add(usersPanel);
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
        usersPanel.addMouseListener(new MouseAdapter()
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

    public void updateRoster()
    {
        // The roster is needed when we do operations on the user list
        usersPanel.setRoster(xmppAuthentication.getConnection().getEndpoint().getRoster());
    }

    /**
     * Does a user list cleanup and then disconnects from the XMPP server
     *
     * TODO: Move parts of it to controller?
     */
    public void doLogout()
    {
        // The cleanup must be done prior to the disconnect because it depends on the connection
        // in order to work properly
        usersPanel.cleanup();

        xmppAuthentication.getConnection().disconnect();
    }

    /**
     * When this is called a new window will be launched that will allow the users to send instant messages to the
     * selected user
     *
     * TODO: Move to controller
     *
     * @param user User to start the conversation with
     */
    private void startNewConversation(User user)
    {
//        Conversation conversationFrame = new Conversation(xmppAuthentication.getConnection(), user);
//        conversationFrame.setLocalUser((User) usersManager.getUser());
    }

    /**
     * Retrieves the users from the XMPP roster and adds them to the user list panel
     *
     */
    public void loadUsers()
    {
        usersPanel.addUsers();
    }

    /**
     * The listener handles the actions that will be done after the user authenticates
     *
     * TODO: Move to controller
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-04-22
     */
    private class AuthenticationStatusListener implements AuthenticationListener
    {
        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
//            if (e.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
//                usersManager.setUser(e.getUser());
//            }
        }
    }
}
