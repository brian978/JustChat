package com.justchat.client;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.client.frame.Contacts;
import com.justchat.client.frame.Login;
import com.justchat.client.frame.menu.MainMenu;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        login.addWindowListener(exitListener);
        login.setMenu(new MainMenu());
        login.initialize();

        // Contacts frame
        contacts.addWindowListener(exitListener);
        contacts.setMenu(new MainMenu());
        contacts.initialize();

        /**
         * --------------------------
         * Login frame listeners
         * --------------------------
         */
        login.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                contacts.updateRoster();
                contacts.loadUsers();
                contacts.showFrame();
            }
        });

        /**
         * --------------------------
         * Contacts frame listeners
         * --------------------------
         */
        contacts.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);

                Dimension size = contacts.getSize();
                settings.set("ContactsWidth", String.valueOf((int) size.getWidth()));
                settings.set("ContactsHeight", String.valueOf((int) size.getHeight()));

                contacts.doLogout();
                contacts.invalidate();
                login.setVisible(true);
            }
        });

        /**
         * -------------------------
         * Loading the software
         * -------------------------
         */
        login.showFrame();
    }

    protected void setAuthenticationObject(Authentication xmppAuthentication)
    {
        if(login.getAuthentication() != null) {
            login.removeAuthenticationListeners();
        }

        if(contacts.getAuthentication() != null) {
            contacts.removeAuthenticationListeners();
        }

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
