package com.justchat.client.frame;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationAwareInterface;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.gui.swing.frame.AbstractFrame;
import com.acamar.net.ConnectionAwareInterface;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
abstract public class AbstractMainFrame extends AbstractFrame
        implements AuthenticationAwareInterface, ConnectionAwareInterface
{
    protected MainMenu menu;
    protected Properties settings;
    protected Authentication xmppAuthentication;
    protected Connection xmppConnection;

    public AbstractMainFrame(String title, Properties settings)
    {
        super(title);

        this.settings = settings;
    }

    abstract protected void populateFrame();

    protected void setupEvents()
    {
        /**
         * -----------------
         * Menu setup
         * ----------------
         */
        // Preferences action
        menu.findItemByName("preferencesItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Launching preferences window from " + AbstractMainFrame.this.getTitle());
            }
        });

        // Exit action
        menu.findItemByName("exitItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (menu.getParentWindow((JMenuItem) e.getSource()) == AbstractMainFrame.this) {
                    triggerClosingEvent();
                }
            }
        });
    }

    public AbstractMainFrame initialize()
    {
        configureFrame();
        populateFrame();
        setupEvents();

        return this;
    }

    @Override
    public AbstractMainFrame setAuthentication(AbstractAuthentication authentication)
    {
        this.xmppAuthentication = (Authentication) authentication;

        return this;
    }

    @Override
    public AbstractMainFrame setConnection(com.acamar.net.Connection connection)
    {
        this.xmppConnection = (Connection) connection;

        return this;
    }

    public void setMenu(MainMenu menu)
    {
        this.menu = menu;
    }
}
