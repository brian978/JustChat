package com.justchat.client.frame;

import com.acamar.authentication.AbstractAsyncAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.panel.AbstractPanel;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;
import com.acamar.net.ConnectionStatusListener;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.TimerTask;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Login extends AbstractFrame
{
    Properties preferences = new Properties("preferences.properties");
    AbstractAsyncAuthentication authentication = null;
    Connection xmppConnection = null;

    // Frames
    Contacts contactsFrame;

    // Panels
    MainMenu menu = new MainMenu();
    LoginPanel loginPanel = new LoginPanel();

    public Login()
    {
        super("JustChat");

        // First thing we need is have the preferences file loaded/created to be able to populate/configure the frame
        preferences.checkAndLoad();

        // Adding the components on the frame
        configureFrame();
        populateFrame();

        // Displaying the frame
        showFrame();

        // Creating the connection objects
        xmppConnection = new Connection();
        xmppConnection.addConnectionStatusListener(new ConnectionStatus());

        // Creating the authentication objects (we must first connect to the server before we can do this
        authentication = new Authentication(xmppConnection);
        authentication.addAuthenticationListener(new AuthenticationStatusListener());

        // Creating the other contacts frame
        contactsFrame = new Contacts(preferences, xmppConnection);

        // The setup events depends on the contacts frame
        setupEvents();
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

        /**
         * -------------
         * Login panel
         * -------------
         */
        loginPanel.setName("loginPanel");
        add(loginPanel);
    }

    private void setupEvents()
    {
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
                    handleAuthenticateAction(loginBtn, identifier, password);
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
                    handleAuthenticateAction(loginBtn, identifier, password);
                }
            }
        });

        /**
         * -----------------------
         * Exit command
         * -----------------------
         */
        JMenuItem item = menu.findItemByName("exitItem");
        if (item != null) {
            item.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    triggerClosingEvent();
                }
            });
        }

        /**
         * -----------------------
         * Login frame
         * -----------------------
         */
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                contactsFrame.loadUsers();
                contactsFrame.showFrame();
            }
        });

        /**
         * -----------------------
         * Contacts frame
         * -----------------------
         */
        contactsFrame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentHidden(ComponentEvent e)
            {
                super.componentHidden(e);
                contactsFrame.invalidate();
                setVisible(true);
            }
        });

        // Frame events
        addWindowListener(new SaveOnExitListener());
    }

    private void handleAuthenticateAction(JButton loginBtn, JTextField identityField, JPasswordField passwordField)
    {
        JLabel infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
        infoLabel.setText("<html><center>Logging you in, please wait...");

        loginBtn.setEnabled(false);
        passwordField.setEnabled(false);

        authentication.authenticate(identityField.getText(), passwordField.getPassword());
        passwordField.setText("");
    }

    private void connectToServer()
    {
        AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");
        JLabel infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
        infoLabel.setText("<html><center>Connecting, please wait...");
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        JLabel infoLabel;
        JButton loginBtn;
        JPasswordField password;

        public AuthenticationStatusListener()
        {
            infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
            loginBtn = (JButton) loginPanel.findComponent("loginBtn");
            password = (JPasswordField) loginPanel.findComponent("passwordField");
        }

        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.isAuthenticated()) {
                contactsFrame.getUsersManager().setCurrentUser(e.getUser());
                password.setEnabled(true);
                setVisible(false);
            } else {
                loginBtn.setEnabled(true);
                password.setEnabled(true);
                infoLabel.setVisible(true);
                infoLabel.setText("<html><center>" + e.getMessage());
            }
        }
    }

    private class ConnectionStatus implements ConnectionStatusListener
    {
        JLabel infoLabel;
        JButton loginBtn;
        java.util.Timer timer = new java.util.Timer();

        public ConnectionStatus()
        {
            infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
            loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        }

        @Override
        public void statusChanged(ConnectionEvent e)
        {
            if (e.getStatusCode() == ConnectionEvent.CONNECTION_OPENED) {
                infoLabel.setVisible(false);
                loginBtn.setEnabled(true);
            } else if (e.getStatusCode() == ConnectionEvent.CONNECTION_CLOSED) {
                infoLabel.setVisible(true);
                loginBtn.setEnabled(false);
                infoLabel.setText("<html><center>" + e.getMessage());

                connectToServer();
            } else {
                infoLabel.setVisible(true);
                infoLabel.setText("<html><center>" + e.getMessage());
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

            if (xmppConnection.getEndpoint().isConnected()) {
                try {
                    xmppConnection.disconnect();
                } catch (ConnectionException e1) {
                    e1.printStackTrace();
                }
            }

            Dimension size = ((AbstractFrame) e.getSource()).getSize();
            preferences.set("MainWidth", String.valueOf((int) size.getWidth()));
            preferences.set("MainHeight", String.valueOf((int) size.getHeight()));

            try {
                preferences.store();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
