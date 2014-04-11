package com.justchat.client.frame;

import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.menu.AbstractMenu;
import com.acamar.gui.panel.AbstractPanel;
import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;
import com.acamar.service.authentication.AuthenticationListener;
import com.acamar.service.provider.openfire.authentication.Authentication;
import com.acamar.websocket.AsyncConnection;
import com.acamar.websocket.ConnectionEvent;
import com.acamar.websocket.ConnectionStatusListener;
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
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Main extends AbstractFrame
{
    MainFramePreferences preferences = new MainFramePreferences();
    AsyncAbstractAuthentication authentication = null;
    AsyncConnection connection = null;

    User user = null;
    Users users = new Users();

    public Main()
    {
        super("JustChat");

        authentication = new Authentication();
        connection = new AsyncConnection();

        // Adding the components on the frame
        configureFrame();
        populateFrame();
        setupEvents();

        // Adding the listeners to our objects
        authentication.addAuthenticationListener(new AuthenticationStatusListener());
        connection.addConnectionStatusListener(new ConnectionStatus());

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
        new Conversation(connection);
    }

    private void setupEvents()
    {
        final AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

        final JTextField identifier = (JTextField) loginPanel.findComponent("identifierField");
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");

        loginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("doLogin")) {
                    loginBtn.setEnabled(false);
                    authentication.authenticate(identifier.getText(), password.getPassword());
                    password.setText("");
                }
            }
        });

        addWindowListener(new SaveOnExitListener());
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

        connection.connect();
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
            } else {
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

        @Override
        public void messageReceived(ConnectionEvent e)
        {

        }
    }

    private class SaveOnExitListener implements WindowListener
    {
        /**
         * Invoked the first time a window is made visible.
         *
         * @param e
         */
        @Override
        public void windowOpened(WindowEvent e)
        {

        }

        /**
         * Invoked when the user attempts to close the window
         * from the window's system menu.
         *
         * @param e
         */
        @Override
        public void windowClosing(WindowEvent e)
        {
            Dimension size = ((AbstractFrame) e.getSource()).getSize();
            preferences.set("MainWidth", String.valueOf((int) size.getWidth()));
            preferences.set("MainHeight", String.valueOf((int) size.getHeight()));

            try {
                preferences.getStorage().store();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        /**
         * Invoked when a window has been closed as the result
         * of calling dispose on the window.
         *
         * @param e
         */
        @Override
        public void windowClosed(WindowEvent e)
        {

        }

        /**
         * Invoked when a window is changed from a normal to a
         * minimized state. For many platforms, a minimized window
         * is displayed as the icon specified in the window's
         * iconImage property.
         *
         * @param e
         * @see java.awt.Frame#setIconImage
         */
        @Override
        public void windowIconified(WindowEvent e)
        {

        }

        /**
         * Invoked when a window is changed from a minimized
         * to a normal state.
         *
         * @param e
         */
        @Override
        public void windowDeiconified(WindowEvent e)
        {

        }

        /**
         * Invoked when the Window is set to be the active Window. Only a Frame or
         * a Dialog can be the active Window. The native windowing system may
         * denote the active Window or its children with special decorations, such
         * as a highlighted title bar. The active Window is always either the
         * focused Window, or the first Frame or Dialog that is an owner of the
         * focused Window.
         *
         * @param e
         */
        @Override
        public void windowActivated(WindowEvent e)
        {

        }

        /**
         * Invoked when a Window is no longer the active Window. Only a Frame or a
         * Dialog can be the active Window. The native windowing system may denote
         * the active Window or its children with special decorations, such as a
         * highlighted title bar. The active Window is always either the focused
         * Window, or the first Frame or Dialog that is an owner of the focused
         * Window.
         *
         * @param e
         */
        @Override
        public void windowDeactivated(WindowEvent e)
        {

        }
    }
}
