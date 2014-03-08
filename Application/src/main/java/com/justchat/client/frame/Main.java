package com.justchat.client.frame;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.gui.panel.UserListPanel;
import com.justchat.model.user.identity.User;
import com.justchat.client.service.websocket.ConnectionHandler;
import com.justchat.client.websocket.factory.ConnectionFactory;
import com.justchat.event.EventObject;
import com.justchat.event.EventsManager;
import com.justchat.event.listener.EventListener;
import com.justchat.gui.frame.AbstractFrame;
import com.justchat.gui.menu.AbstractMenu;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.gui.panel.AbstractPanel;
import com.justchat.service.AuthenticationInterface;
import com.justchat.client.service.provider.facebook.Authentication;
import com.justchat.util.Properties;
import sun.awt.WindowClosingListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Main extends AbstractFrame
{
    Properties preferences = new Properties("preferences.properties");
    boolean preferencesLoaded = false;
    AuthenticationInterface authentication = null;
    EventsManager eventsManager = new EventsManager();
    User user = null;

    public Main()
    {
        super("JustChat");

        try {
            authentication = new Authentication(eventsManager, ConnectionFactory.factory(eventsManager));
        } catch (FailedToLoadConfigurationException e) {
            e.printStackTrace();
        }

        preferencesLoaded = preferences.checkAndLoad();

        configureFrame();
        populateFrame();
        showFrame();
        ensureMinimumSize();
        setupEvents();

        Thread connectionHandler = new Thread(new ConnectionHandler(eventsManager, authentication));
        connectionHandler.start();
    }

    protected void configureFrame()
    {
        super.configureFrame();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        if (preferencesLoaded && preferences.get("MainWidth") != null) {
            setPreferredSize(
                    new Dimension(
                            Integer.parseInt(preferences.get("MainWidth").toString()),
                            Integer.parseInt(preferences.get("MainHeight").toString())
                    )
            );
        }
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(new Dimension(200, 500));
    }

    @Override
    protected void populateFrame()
    {
        GridBagConstraints c;

        /**
         * -------------
         * main menu
         * -------------
         */
        MainMenu mainMenu = new MainMenu();

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;

        setJMenuBar(mainMenu);
        attachMenuListeners(mainMenu);

        /**
         * -------------
         * Login panel
         * -------------
         */
        LoginPanel loginPanel = new LoginPanel(authentication);
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
        UserListPanel userListPanel = new UserListPanel();
        userListPanel.setName("userListPanel");

        add(userListPanel);

        // Repainting
        revalidate();
        pack();
        repaint();
    }

    private void setupEvents()
    {
        final Main currentFrame = this;
        final AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

        final JLabel infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
        final JTextField identifier = (JTextField) loginPanel.findComponent("identifierField");
        final JPasswordField password = (JPasswordField) loginPanel.findComponent("passwordField");
        final JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");

        eventsManager.attach("connectionStatus", new EventListener()
        {
            @Override
            public <T> void handleEvent(EventObject<T> event)
            {
                String status = (String) event.getParameters().get("status");

                if (status.equals("success")) {
                    infoLabel.setVisible(false);
                    loginBtn.setEnabled(true);
                } else {
                    infoLabel.setText("<html><center>Connection failed");
                }
            }
        });

        eventsManager.attach("authenticationStatus", new EventListener()
        {
            @Override
            public <T> void handleEvent(EventObject<T> event)
            {
                String status = (String) event.getParameters().get("status");

                if (status.equals("success")) {
                    currentFrame.setUser((User) event.getParameters().get("user"));
                    currentFrame.showUserList();
                } else {
                    loginBtn.setEnabled(true);
                    infoLabel.setVisible(true);
                    infoLabel.setText("<html><center>" + event.getParameters().get("message"));
                }
            }
        });

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
            preferences.setProperty("MainWidth", String.valueOf((int) size.getWidth()));
            preferences.setProperty("MainHeight", String.valueOf((int) size.getHeight()));

            try {
                preferences.store();
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
