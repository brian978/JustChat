package com.justchat.client.frame;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.gui.swing.panel.AbstractPanel;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionStatusListener;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.AuthenticatePanel;
import com.justchat.client.gui.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.TimerTask;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Login extends AbstractMainFrame
{
    // Panels
    private LoginPanel loginPanel = new LoginPanel();
    private AuthenticatePanel authenticatePanel = new AuthenticatePanel();

    public Login(Properties settings)
    {
        super("JustChat", settings);
    }

    public Login addAuthenticationListeners()
    {
        xmppAuthentication.addAuthenticationListener(new AuthenticationStatusListener());

        return this;
    }

    public Login addConnectionListeners()
    {
        xmppConnection.addConnectionStatusListener(new ConnectionStatus());

        return this;
    }

    protected void configureFrame()
    {
        super.configureFrame();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(new Dimension(200, 500));
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

        /**
         * -------------
         * Authenticate panel
         * -------------
         */
        authenticatePanel.setName("authenticatePanel");
        authenticatePanel.setVisible(false);
        add(authenticatePanel);

        // Pre-filling the server and port fields
        ((JTextField) loginPanel.findComponent("serverField")).setText(xmppConnection.getHost());
        ((JTextField) loginPanel.findComponent("portField")).setText(String.valueOf(xmppConnection.getPort()));
    }

    @Override
    protected void setupEvents()
    {
        super.setupEvents();

        // Buttons and fields events
        final JTextField identifier = (JTextField) loginPanel.findComponent("identifierField");
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");

        // Password field key actions
        password.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == 10) {
                    handleAuthenticateAction(identifier, password);
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
                    handleAuthenticateAction(identifier, password);
                }
            }
        });
    }

    private void handleAuthenticateAction(JTextField identityField, JPasswordField passwordField)
    {
        loginPanel.setVisible(false);
        authenticatePanel.setVisible(true);
        revalidate();

        // Storing the configuration of the connection
        JTextField serverField, portField;
        serverField = (JTextField) loginPanel.findComponent("serverField");
        portField = (JTextField) loginPanel.findComponent("portField");

        // Will also store the settings
        xmppConnection.setup(serverField.getText(), Integer.parseInt(portField.getText()));

        // Now we authenticate
        xmppAuthentication.authenticateAsync(identityField.getText(), passwordField.getPassword());
        passwordField.setText("");
    }

    private void connectToServer()
    {
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        JButton loginBtn;
        JPasswordField password;

        public AuthenticationStatusListener()
        {
            loginBtn = (JButton) loginPanel.findComponent("loginBtn");
            password = (JPasswordField) loginPanel.findComponent("passwordField");
        }

        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.isAuthenticated()) {
                setVisible(false);
            } else {
                loginPanel.setVisible(true);
                authenticatePanel.setVisible(false);
                revalidate();
            }
        }
    }

    private class ConnectionStatus implements ConnectionStatusListener
    {
        JButton loginBtn;
        java.util.Timer timer = new java.util.Timer();

        public ConnectionStatus()
        {
            loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        }

        @Override
        public void statusChanged(ConnectionEvent e)
        {
            if (e.getStatusCode() == ConnectionEvent.CONNECTION_OPENED) {
                loginBtn.setEnabled(true);
            } else if (e.getStatusCode() == ConnectionEvent.CONNECTION_CLOSED) {
                loginBtn.setEnabled(false);

                connectToServer();
            } else {
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        connectToServer();
                    }
                }, 3000);
            }
        }
    }
}
