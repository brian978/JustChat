package com.justchat;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventManager;
import com.acamar.util.Properties;
import com.justchat.view.frame.Contacts;
import com.justchat.view.frame.Login;
import com.justchat.view.frame.menu.MainMenu;
import com.justchat.view.panel.LoginPanel;
import com.justchat.view.panel.components.CommunicationServiceItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-02
 */
public class Launcher
{
    Properties settings = new Properties("preferences.properties");
    Login login = new Login(settings);
    Contacts contacts = new Contacts(settings);

    EventManager eventManager = new EventManager();

    public static void main(String[] args)
    {
        new Launcher();
    }

    public Launcher()
    {
        // Loading (or creating) the preferences file
        // TODO: handle read/create file failed (maybe with an error popup?)
        boolean settingsLoaded = settings.checkAndLoad();

        if (!settingsLoaded) {
            System.exit(-10);
        }

        /**
         * --------------------------
         * Frames setup
         * --------------------------
         */
        // Frame listeners
        SaveOnExitListener exitListener = new SaveOnExitListener();

        // Login frame
        login.getViewContainer().addWindowListener(exitListener);
        login.setMenu(new MainMenu());
        login.initialize();

        // Contacts frame
        contacts.getViewContainer().addWindowListener(exitListener);
        contacts.setMenu(new MainMenu());
        contacts.initialize();

        /**
         * --------------------------
         * Login frame listeners
         * --------------------------
         */
        login.getViewContainer().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                contacts.updateRoster();
                contacts.loadUsers();
                contacts.display();
            }
        });

        // Deciding what to do when we select a different communication server
        LoginPanel loginPanel = (LoginPanel) login.findComponent("loginPanel");
        final JComboBox connection = (JComboBox) loginPanel.findComponent("connectionField");

        // Selecting the authentication object of the first item because by default that is what we will have pre-filled
        setAuthenticationObject((CommunicationServiceItem) ((JComboBox) loginPanel.findComponent("connectionField")).getItemAt(0));

        connection.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setAuthenticationObject((CommunicationServiceItem) connection.getSelectedItem());
            }
        });

        /**
         * --------------------------
         * Contacts frame listeners
         * --------------------------
         */
        contacts.getViewContainer().addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);

                Dimension size = contacts.getViewContainer().getSize();
                settings.set("ContactsWidth", String.valueOf((int) size.getWidth()));
                settings.set("ContactsHeight", String.valueOf((int) size.getHeight()));

                contacts.doLogout();
                contacts.getViewContainer().invalidate();
                login.getViewContainer().setVisible(true);
            }
        });

        /**
         * -------------------------
         * Loading the software
         * -------------------------
         */
        login.display();
    }

    protected void setAuthenticationObject(CommunicationServiceItem serviceItem)
    {
        Authentication authentication = null;

        try {
            authentication = serviceItem.getInstance();
        } catch (IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }

        if (authentication != null) {
            setAuthenticationObject(authentication);
        }
    }

    protected void setAuthenticationObject(Authentication xmppAuthentication)
    {
        if (login.getAuthentication() != null) {
            login.removeAuthenticationListeners();
        }

        if (contacts.getAuthentication() != null) {
            contacts.removeAuthenticationListeners();
        }

        xmppAuthentication.setEventManager(eventManager);
        xmppAuthentication.getConnection().setEventManager(eventManager);

        login.setAuthentication(xmppAuthentication);
        login.addAuthenticationListeners();

        contacts.setAuthentication(xmppAuthentication);
        contacts.addAuthenticationListeners();
    }

    private class SaveOnExitListener extends WindowAdapter
    {
        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         *
         * @param e WindowEvent object used to determine properties of the window
         */
        @Override
        public void windowClosing(WindowEvent e)
        {
            System.out.println("Cleaning up the main program");

            try {
                settings.store();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
