package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.event.listener.EventListenerInterface;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.mvc.event.MvcEvent;
import com.justchat.mvc.view.frame.Login;
import com.justchat.mvc.view.panel.AuthenticatePanel;
import com.justchat.mvc.view.panel.LoginPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import javax.swing.*;
import java.awt.event.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class LoginController extends AbstractController
{
    private Login loginFrame = new Login();

    /**
     * The method should be called when all the necessary configuration has been done on the controller
     */
    public void completeSetup()
    {
        if (!setupCompleted) {
            // We must first inject the event manager into the panels because when we initialize the frame
            // the panels will also be populated with objects
            loginFrame.getLoginPanel().setEventManager(eventManager);

            // Initializing the frame and selecting a default communication service
            initializeFrame();
        }
    }

    /**
     * Attaches a set of events to the event listener
     * <p/>
     * Called after the frame has been initialized
     */
    private void attachEvents()
    {
        /**
         * -----------------------
         * Event manager events
         * -----------------------
         */
        eventManager.attach(AuthenticationEvent.class, new AuthenticationEventsListener());

        /**
         * -----------------------
         * Components events
         * -----------------------
         */
        LoginPanel loginPanel = loginFrame.getLoginPanel();
        AuthenticatePanel authenticatePanel = loginFrame.getAuthenticatePanel();

        // Communication service changed
        final JComboBox connectionField = (JComboBox) loginPanel.findComponent("connectionField");
        connectionField.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                loginFrame.prefillData((CommunicationServiceItem) connectionField.getSelectedItem());
            }
        });

        // Password field key actions
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        password.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == 10) {
                    performAuthentication();
                }
            }
        });

        // Login button action
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        loginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("doLogin")) {
                    performAuthentication();
                }
            }
        });

        // Cancel button action
        final JButton cancelBtn = (JButton) authenticatePanel.findComponent("cancelBtn");
        cancelBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Authentication authentication = getAuthentication();
                if (authentication.cancel()) {
                    loginFrame.toggleMainPanels();
                } else {
                    // To prevent the user from spamming the button
                    cancelBtn.setEnabled(false);
                }
            }
        });

        // We also have from frame events that will trigger events on the event manager
        loginFrame.getViewContainer().addWindowListener(new WindowAdapter()
        {
            /**
             * Invoked when a window has been closed.
             *
             * @param e Window event object
             */
            @Override
            public void windowClosing(WindowEvent e)
            {
                super.windowClosed(e);
                eventManager.trigger(new MvcEvent(MvcEvent.WINDOW_CLOSING, loginFrame));
            }
        });
    }

    /**
     * Calls the initialize() method on the login frame and then attaches the event listeners
     */
    private void initializeFrame()
    {
        loginFrame.initialize();

        // We need to populate the fields by default
        JComboBox jComboBox = ((JComboBox) loginFrame.getLoginPanel().findComponent("connectionField"));
        loginFrame.prefillData((CommunicationServiceItem) jComboBox.getItemAt(0));

        // Now that we have all the elements on the frame we need to attach some events on it
        attachEvents();
    }

    /**
     * Retrieves the selected communication object and extracts the authentication object from it
     *
     * @return Authentication
     */
    private Authentication getAuthentication()
    {
        JComboBox jComboBox = ((JComboBox) loginFrame.getLoginPanel().findComponent("connectionField"));
        Authentication authentication = null;

        try {
            authentication = ((CommunicationServiceItem) jComboBox.getItemAt(0)).getInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return authentication;
    }

    /**
     * It handles the connection setup and calls the authentication method on the proper object
     * <p/>
     * The method is called when the user clicks the login button in the login panel
     */
    private void performAuthentication()
    {
        // Getting the authentication object
        Authentication authentication = getAuthentication();

        // Performing the authentication
        if (authentication != null) {
            // Switching the panels so the user knows what's happening
            loginFrame.toggleMainPanels();

            LoginPanel loginPanel = loginFrame.getLoginPanel();

            // Login data
            JTextField identityField = (JTextField) loginPanel.findComponent("identifierField");
            JPasswordField passwordField = (JPasswordField) loginPanel.findComponent("passwordField");

            // Getting the connection info so we can setup the connection object
            JTextField serverField, portField, resourceField;
            serverField = (JTextField) loginPanel.findComponent("serverField");
            portField = (JTextField) loginPanel.findComponent("portField");
            resourceField = (JTextField) loginPanel.findComponent("resourceField");

            // The setup will also store the settings
            authentication.getConnection()
                          .setup(serverField.getText(), Integer.parseInt(portField.getText()), resourceField.getText());

            // Now we authenticate (async so we don't block the interface)
            authentication.authenticateAsync(identityField.getText(), passwordField.getPassword());
            passwordField.setText("");
        }
    }

    /**
     * When called, the login frame will be displayed
     */
    public void displayLogin()
    {
        loginFrame.display();
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-03-03
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
            // If the login is successful we hide the current frame (since we don't need it for now)
            if (((AuthenticationEvent) e).getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                loginFrame.getViewContainer().setVisible(false);
            }

            // Since the login was done we need to revert what we show on this frame to the original state
            // in case the user logs out
            loginFrame.toggleMainPanels();
        }
    }
}
