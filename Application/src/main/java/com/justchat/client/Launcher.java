package com.justchat.client;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.client.frame.Contacts;
import com.justchat.client.frame.Login;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-02
 */
public class Launcher
{
    Connection xmppConnection;
    Authentication xmppAuthentication;

    Properties preferences = new Properties("preferences.properties");
    Login login = new Login(preferences);
    Contacts contacts = new Contacts(preferences);

    public static void main(String[] args)
    {
        new Launcher();
    }

    public Launcher()
    {
        // Loading (or creating) the preferences file
        // TODO: handle read/create file failed (maybe with an error popup?)
        preferences.checkAndLoad();

        /**
         * --------------------------
         * Connection setup
         * --------------------------
         */
        xmppConnection = new Connection();

        /**
         * --------------------------
         * Authentication setup
         * --------------------------
         */
        xmppAuthentication = new Authentication(xmppConnection);

        /**
         * --------------------------
         * Frames setup
         * --------------------------
         */
        login.setConnection(xmppConnection)
             .setAuthentication(xmppAuthentication);

        login.addConnectionListeners()
             .addAuthenticationListeners();

        contacts.setConnection(xmppConnection)
                .setAuthentication(xmppAuthentication);

        contacts.addAuthenticationListeners();

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
}
