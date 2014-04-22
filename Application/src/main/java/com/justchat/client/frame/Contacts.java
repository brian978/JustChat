package com.justchat.client.frame;

import com.acamar.gui.frame.AbstractFrame;
import com.acamar.net.xmpp.Connection;
import com.acamar.users.UsersManager;
import com.justchat.client.frame.menu.ContactsMenu;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.client.gui.panel.UserListPanel;
import com.justchat.client.gui.panel.components.UserList;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-22
 */
public class Contacts extends AbstractFrame
{
    ContactsMenu menu = new ContactsMenu();

    Connection xmppConnection;
    UsersManager usersManager = new UsersManager();

    public Contacts (Connection xmppConnection)
    {
        super("JustChat - Contacts");

        this.xmppConnection = xmppConnection;
    }

    @Override
    protected void populateFrame()
    {
//        add(userListPanel);
//
//        // Updating the window dimensions to what the user last set
//        setPreferredSize(getSizePreferences());
//
//        // Listeners for the panel
//        userListPanel.addMouseListener(new MouseAdapter()
//        {
//            /**
//             * {@inheritDoc}
//             *
//             * @param e
//             */
//            @Override
//            public void mouseClicked(MouseEvent e)
//            {
//                super.mouseClicked(e);
//
//                if (e.getClickCount() == 2) {
//                    startNewConversation(((UserList) e.getSource()).getSelectedValue());
//                }
//            }
//        });
//
//        userListPanel.addUsers(xmppConnection.getEndpoint().getRoster().getEntries());
    }
}
