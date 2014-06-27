package com.justchat;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.EventListenerInterface;
import com.acamar.event.EventManager;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.util.Properties;
import com.justchat.mvc.controller.LoginController;
import com.justchat.mvc.view.frame.Contacts;
import com.justchat.mvc.view.frame.Login;
import com.justchat.mvc.view.frame.menu.MainMenu;
import com.justchat.mvc.view.panel.LoginPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

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
    SaveOnExitListener exitListener = new SaveOnExitListener();
    EventManager eventManager = new EventManager();
    LoginController loginController = new LoginController(eventManager);

//    Login login = new Login(settings);
//    Contacts contacts = new Contacts(settings);

    /**
     * The method creates a new instance of the Launcher object
     *
     * @param args Arguments for the launcher
     */
    public static void main(String[] args)
    {
        new Launcher();
    }

    /**
     * Creates a launcher object and configures it
     */
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
         * Event listeners
         * --------------------------
         */
        eventManager.attach(MvcEvent.WINDOW_CLOSING, exitListener);


        /**
         * --------------------------
         * Running the application
         * --------------------------
         */
        loginController.displayLogin();

        /**
         * --------------------------
         * Login frame listeners
         * --------------------------
         */
//        login.getViewContainer().addComponentListener(new ComponentAdapter()
//        {
//            @Override
//            public void componentHidden(ComponentEvent e)
//            {
//                super.componentHidden(e);
//                contacts.updateRoster();
//                contacts.loadUsers();
//                contacts.display();
//            }
//        });
//
//        // Deciding what to do when we select a different communication server
//        LoginPanel loginPanel = (LoginPanel) login.findComponent("loginPanel");
//        final JComboBox connection = (JComboBox) loginPanel.findComponent("connectionField");
//
//        // Selecting the authentication object of the first item because by default that is what we will have pre-filled
//        setAuthenticationObject((CommunicationServiceItem) ((JComboBox) loginPanel.findComponent("connectionField")).getItemAt(0));
//
//        connection.addActionListener(new ActionListener()
//        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                setAuthenticationObject((CommunicationServiceItem) connection.getSelectedItem());
//            }
//        });
//
//        /**
//         * --------------------------
//         * Contacts frame listeners
//         * --------------------------
//         */
//        contacts.getViewContainer().addComponentListener(new ComponentAdapter()
//        {
//            @Override
//            public void componentHidden(ComponentEvent e)
//            {
//                super.componentHidden(e);
//
//                Dimension size = contacts.getViewContainer().getSize();
//                settings.set("ContactsWidth", String.valueOf((int) size.getWidth()));
//                settings.set("ContactsHeight", String.valueOf((int) size.getHeight()));
//
//                contacts.doLogout();
//                contacts.getViewContainer().invalidate();
//                login.getViewContainer().setVisible(true);
//            }
//        });
//
//        /**
//         * -------------------------
//         * Loading the software
//         * -------------------------
//         */
//        login.display();
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
//        if (login.getAuthentication() != null) {
//            login.removeAuthenticationListeners();
//        }
//
//        if (contacts.getAuthentication() != null) {
//            contacts.removeAuthenticationListeners();
//        }
//
//        xmppAuthentication.setEventManager(eventManager);
//        xmppAuthentication.getConnection().setEventManager(eventManager);
//
//        login.setAuthentication(xmppAuthentication);
//        login.addAuthenticationListeners();
//
//        contacts.setAuthentication(xmppAuthentication);
//        contacts.addAuthenticationListeners();
    }

    /**
     * The event listener will handle window closing events
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-27
     */
    private class SaveOnExitListener implements EventListenerInterface
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            System.out.println("Handling the " + e.getName() + " event");

            try {
                settings.store();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
