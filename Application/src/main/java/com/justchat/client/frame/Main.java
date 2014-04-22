package com.justchat.client.frame;

import com.acamar.authentication.AbstractAsyncAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.menu.AbstractMenu;
import com.acamar.gui.panel.AbstractPanel;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;
import com.acamar.net.ConnectionStatusListener;
import com.acamar.net.xmpp.Connection;
import com.acamar.users.User;
import com.acamar.users.UsersManager;
import com.acamar.util.Properties;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.client.gui.panel.UserListPanel;
import com.justchat.client.gui.panel.components.UserList;

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
public class Main extends AbstractFrame
{
    Properties preferences = new Properties("preferences.properties");
    AbstractAsyncAuthentication authentication = null;
    Connection xmppConnection = null;
    UsersManager usersManager = new UsersManager();

    // Panels
    MainMenu mainMenu = new MainMenu();
    LoginPanel loginPanel = new LoginPanel();
    UserListPanel userListPanel = new UserListPanel(usersManager);

    public Main()
    {
        super("JustChat");

        // First thing we need is have the preferences file loaded/created to be able to populate/configure the frame
        preferences.checkAndLoad();

        // Adding the components on the frame
        configureFrame();
        populateFrame();
        setupEvents();

        // Displaying the frame
        showFrame();
        ensureMinimumSize();

        // Creating the connection objects
        xmppConnection = new Connection();
        xmppConnection.addConnectionStatusListener(new ConnectionStatus());

        // Creating the authentication objects (we must first connect to the server before we can do this
        authentication = new Authentication(xmppConnection);
        authentication.addAuthenticationListener(new AuthenticationStatusListener());
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

    @Override
    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        setJMenuBar(mainMenu);
        attachMenuListeners(mainMenu);

        // Updating panels
        loginPanel.setName("loginPanel");
        userListPanel.setName("userListPanel");

        // By default we show the login panel
        showLoginPanel();
    }

    protected void attachMenuListeners(AbstractMenu menu)
    {
        final JFrame currentFrame = this;
        JMenuItem item;

        /**
         * -----------------------
         * Listener exit command
         * -----------------------
         */
        item = menu.findItemByName("exitItem");
        if (item != null) {
            item.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    currentFrame.dispatchEvent(new WindowEvent(currentFrame, WindowEvent.WINDOW_CLOSING));
                }
            });
        }
    }

    private void showLoginPanel()
    {
        /**
         * -------------
         * Login panel
         * -------------
         */
        add(loginPanel);

        // Repainting
        revalidate();
        repaint();
    }

    private void showUserList()
    {
        remove(loginPanel);

        /**
         * ----------------
         * User list panel
         * ----------------
         */
        add(userListPanel);

        // Updating the window dimensions to what the user last set
        setPreferredSize(getSizePreferences());

        // Listeners for the panel
        userListPanel.addMouseListener(new MouseAdapter()
        {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {
                    startNewConversation(((UserList) e.getSource()).getSelectedValue());
                }
            }
        });

        userListPanel.addUsers(xmppConnection.getEndpoint().getRoster().getEntries());

        // Repainting
        revalidate();
        repaint();
    }

    private void startNewConversation(User user)
    {

        Conversation conversationFrame = new Conversation(xmppConnection, user);
        conversationFrame.setLocalUser(usersManager.getCurrentUser());
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

        // Menu handlers
        mainMenu.findItemByName("logoutItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try {
                    xmppConnection.disconnect();
                } catch (ConnectionException e1) {
                    e1.printStackTrace();
                } finally {
                    remove(userListPanel);
                    usersManager.removeAll();
                    showLoginPanel();
                }
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

    private Dimension getSizePreferences()
    {
        Object width = preferences.get("MainWidth");
        Object height = preferences.get("MainHeight");

        if (width != null && height != null) {
            return new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        return getPreferredSize();
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
                usersManager.setCurrentUser(e.getUser());
                showUserList();
                password.setEnabled(true);
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
