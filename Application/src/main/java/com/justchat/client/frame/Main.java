package com.justchat.client.frame;

import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.menu.AbstractMenu;
import com.acamar.gui.panel.AbstractPanel;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;
import com.acamar.authentication.AbstractAsyncAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.net.ConnectionStatusListener;
import com.acamar.net.xmpp.Connection;
import com.justchat.client.gui.list.UserList;
import com.justchat.client.gui.panel.UserListPanel;
import com.justchat.client.frame.preferences.MainFramePreferences;
import com.justchat.model.user.identity.User;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.model.user.manager.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Main extends AbstractFrame
{
    MainFramePreferences preferences = new MainFramePreferences();
    AbstractAsyncAuthentication authentication = null;
    Connection xmppConnection = null;

    User user = null;
    Users users = new Users();

    public Main()
    {
        super("JustChat");

        xmppConnection = new Connection();
        authentication = new Authentication(xmppConnection.getEndpoint());

        // Adding the components on the frame
        configureFrame();
        populateFrame();
        setupEvents();

        // Adding the listeners to our objects
        authentication.addAuthenticationListener(new AuthenticationStatusListener());
        xmppConnection.addConnectionStatusListener(new ConnectionStatus());

        // Displaying the frame
        showFrame();
        ensureMinimumSize();

        // Finishing the rest of the tasks
        connectToServer();
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
        MainMenu mainMenu = new MainMenu();

        setJMenuBar(mainMenu);
        attachMenuListeners(mainMenu);

        /**
         * -------------
         * Login panel
         * -------------
         */
        LoginPanel loginPanel = new LoginPanel();
        loginPanel.setName("loginPanel");

        add(loginPanel);
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

    private void showUserList()
    {
        // First we need to remove the login panel
        AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");
        if (loginPanel != null) {
            remove(loginPanel);
        }

        // Adding the UserList in place of the login panel
        UserListPanel userListPanel = new UserListPanel(users);
        userListPanel.setName("userListPanel");

        add(userListPanel);

        // Listeners for the panel
        final UserList list = userListPanel.getUserList();
        list.addMouseListener(new MouseAdapter()
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
                    String value = list.getSelectedValue();
                    System.out.println("Selected value is " + value);
                    startNewConversation();
                }
            }
        });

        // Since the login is pretty standard we didn't care about the windows dimensions that were set by the user
        // but now...
        setPreferredSize(preferences.getPreferedSize(getPreferredSize()));

        // Repainting
        revalidate();
        pack();
        repaint();
    }

    private void startNewConversation()
    {
        new Conversation(xmppConnection);
    }

    private void setupEvents()
    {
        // Buttons and fields events
        final AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

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

        // Frame events
        addWindowListener(new SaveOnExitListener());
    }

    private void handleAuthenticateAction(JButton loginBtn, JTextField indentityField, JPasswordField passwordField)
    {
        loginBtn.setEnabled(false);
        authentication.authenticate(indentityField.getText(), passwordField.getPassword());
        passwordField.setText("");
    }

    private void setUser(User user)
    {
        this.user = user;
    }

    private void connectToServer()
    {
        AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");
        JLabel infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
        infoLabel.setText("<html><center>Connecting, please wait...");

        xmppConnection.connectAsync();
    }

    private class AuthenticationStatusListener implements AuthenticationListener
    {
        JLabel infoLabel;
        JButton loginBtn;

        public AuthenticationStatusListener()
        {
            AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

            infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
            loginBtn = (JButton) loginPanel.findComponent("loginBtn");
        }

        @Override
        public void authenticationPerformed(AuthenticationEvent e)
        {
            if (e.isAuthenticated()) {
                setUser(new User("asdsa", "asdf"));
                showUserList();
            } else {
                loginBtn.setEnabled(true);
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
            final AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

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
            try {
                xmppConnection.disconnect();
            } catch (ConnectionException e1) {
                e1.printStackTrace();
            }

            Dimension size = ((AbstractFrame) e.getSource()).getSize();
            preferences.set("MainWidth", String.valueOf((int) size.getWidth()));
            preferences.set("MainHeight", String.valueOf((int) size.getHeight()));

            try {
                preferences.getStorage().store();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
