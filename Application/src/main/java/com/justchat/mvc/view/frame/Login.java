package com.justchat.mvc.view.frame;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.mvc.view.frame.menu.MainMenu;
import com.justchat.mvc.view.panel.AuthenticatePanel;
import com.justchat.mvc.view.panel.LoginPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Login extends AbstractMainFrame
{
    private AuthenticationListener authenticationListener = new AuthenticationStatusListener();

    // Panels
    private LoginPanel loginPanel = new LoginPanel();
    private AuthenticatePanel authenticatePanel = new AuthenticatePanel();

    public Login(Properties settings)
    {
        super("JustChat", settings);
    }

    @Override
    public AbstractMainFrame setAuthentication(AbstractAuthentication authentication)
    {
        super.setAuthentication(authentication);

        // Login data
        prefillData(xmppAuthentication.getConnection());

        return this;
    }

    public Login addAuthenticationListeners()
    {
        xmppAuthentication.addAuthenticationListener(authenticationListener);

        return this;
    }

    public Login removeAuthenticationListeners()
    {
        xmppAuthentication.removeAuthenticationListener(authenticationListener);

        return this;
    }

    protected void configure()
    {
        super.configure();

        container.setLayout(new BoxLayout(container.getContentPane(), BoxLayout.PAGE_AXIS));
    }

    protected void ensureMinimumSize()
    {
        container.setMinimumSize(new Dimension(200, 400));
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

        /**
         * -------------
         * Login panel
         * -------------
         */
        loginPanel.setName("loginPanel");
        container.add(loginPanel);

        // We need to populate the fields by default
        prefillData((CommunicationServiceItem) ((JComboBox) loginPanel.findComponent("connectionField")).getItemAt(0));

        /**
         * -------------
         * Authenticate panel
         * -------------
         */
        authenticatePanel.setName("authenticatePanel");
        authenticatePanel.setVisible(false);
        container.add(authenticatePanel);
    }

    /**
     * Adds event handlers to the events that will be triggered by elements on the frame
     *
     */
    @Override
    protected void setupEvents()
    {
        super.setupEvents();

        // Buttons and fields events
        final JComboBox connection = (JComboBox) loginPanel.findComponent("connectionField");
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        final JButton cancelBtn = (JButton) authenticatePanel.findComponent("cancelBtn");

        // Communication service changed
        connection.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                prefillData((CommunicationServiceItem) connection.getSelectedItem());
            }
        });

        // Password field key actions
        password.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == 10) {
                    handleAuthenticateAction();
                }
            }
        });

        // Login btn action
        loginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("doLogin")) {
                    handleAuthenticateAction();
                }
            }
        });

        // Cancel btn action
        cancelBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (xmppAuthentication.cancel()) {
                    toggleMainPanels();
                }
            }
        });
    }

    /**
     * It handles the connection setup and calls the authentication method on the proper object
     *
     * The method is called when the user clicks the login button in the login panel
     *
     */
    private void handleAuthenticateAction()
    {
        toggleMainPanels();

        // Login data
        JTextField identityField = (JTextField) loginPanel.findComponent("identifierField");
        JPasswordField passwordField = (JPasswordField) loginPanel.findComponent("passwordField");

        // Storing the configuration of the connection
        JTextField serverField, portField, resourceField;
        serverField = (JTextField) loginPanel.findComponent("serverField");
        portField = (JTextField) loginPanel.findComponent("portField");
        resourceField = (JTextField) loginPanel.findComponent("resourceField");

        // Will also store the settings
        xmppAuthentication.getConnection()
                          .setup(serverField.getText(), Integer.parseInt(portField.getText()), resourceField.getText());

        // Now we authenticate (async so we don't block the interface)
        xmppAuthentication.authenticateAsync(identityField.getText(), passwordField.getPassword());
        passwordField.setText("");
    }

    /**
     * The method switches between the authentication and the login panel
     *
     */
    private void toggleMainPanels()
    {
        loginPanel.setVisible(!loginPanel.isVisible());
        authenticatePanel.setVisible(!loginPanel.isVisible());
    }

    /**
     * It handles the first step before the data preparation for the login panel which is the extraction of the
     * connection object from the service item
     *
     * @param serviceItem CommunicationServiceItem
     */
    private void prefillData(CommunicationServiceItem serviceItem)
    {
        Authentication authentication = null;

        try {
            authentication = serviceItem.getInstance();
        } catch (IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }

        if (authentication != null) {
            prefillData(authentication.getConnection());
        }
    }

    /**
     * Prepares the data from the connection object for the login panel that will add it to the UI
     *
     * @param connection Connection
     */
    private void prefillData(Connection connection)
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("serverField", connection.getHost());
        data.put("portField", String.valueOf(connection.getPort()));
        data.put("resourceField", connection.getResource());

        loginPanel.prefill(data);
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-03-03
     */
    private class AuthenticationStatusListener implements AuthenticationListener
    {
        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            // If the login is successful we hide the current frame (since we don't need it for now)
            if (e.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                container.setVisible(false);
            }

            // Since the login was done we need to revert what we show on this frame to the original state
            // in case the user logs out
            loginPanel.setVisible(true);
            authenticatePanel.setVisible(false);
        }
    }
}
