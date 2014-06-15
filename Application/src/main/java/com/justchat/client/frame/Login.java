package com.justchat.client.frame;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionStatusListener;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.AuthenticatePanel;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.client.gui.panel.components.CommunicationServiceItem;

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

    protected void configureFrame()
    {
        super.configureFrame();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(new Dimension(200, 400));
    }

    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        setJMenuBar(menu);
        menu.display(MainMenu.DEFAULT_ITEMS);

        /**
         * -------------
         * Login panel
         * -------------
         */
        loginPanel.setName("loginPanel");
        add(loginPanel);

        // We need to populate the fields by default
        prefillData((CommunicationServiceItem) ((JComboBox) loginPanel.findComponent("connectionField")).getItemAt(0));

        /**
         * -------------
         * Authenticate panel
         * -------------
         */
        authenticatePanel.setName("authenticatePanel");
        authenticatePanel.setVisible(false);
        add(authenticatePanel);
    }

    @Override
    protected void setupEvents()
    {
        super.setupEvents();

        // Buttons and fields events
        final JComboBox connection = (JComboBox) loginPanel.findComponent("connectionField");
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        final JButton cancelBtn = (JButton) authenticatePanel.findComponent("cancelBtn");

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
     */
    private void toggleMainPanels()
    {
        loginPanel.setVisible(!loginPanel.isVisible());
        authenticatePanel.setVisible(!loginPanel.isVisible());
    }

    private void prefillData(CommunicationServiceItem item)
    {
        Authentication authentication = null;

        try {
            authentication = item.getInstance();
        } catch (IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }

        if (authentication != null) {
            prefillData(authentication.getConnection());
        }
    }

    private void prefillData(Connection connection)
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("serverField", connection.getHost());
        data.put("portField", String.valueOf(connection.getPort()));
        data.put("resourceField", connection.getResource());

        loginPanel.prefill(data);
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                setVisible(false);
            }

            loginPanel.setVisible(true);
            authenticatePanel.setVisible(false);
        }
    }
}
