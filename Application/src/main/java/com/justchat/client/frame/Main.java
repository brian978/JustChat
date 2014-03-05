package com.justchat.client.frame;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.service.websocket.ConnectionHandler;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.factory.ConnectionFactory;
import com.justchat.client.websocket.listeners.ConnectionStatusListener;
import com.justchat.gui.frame.AbstractFrame;
import com.justchat.gui.menu.AbstractMenu;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.gui.panel.AbstractPanel;
import com.justchat.service.AuthenticationInterface;
import com.justchat.client.service.provider.facebook.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Main extends AbstractFrame
{
    AuthenticationInterface authentication = null;

    public Main()
    {
        super("JustChat");

        try {
            authentication = new Authentication(ConnectionFactory.factory());
        } catch (FailedToLoadConfigurationException e) {
            e.printStackTrace();
        }

        configureFrame();
        populateFrame();
        showFrame();
        ensureMinimumSize();
        setupEvents();

        // We need the info box for failed messages
        AbstractPanel panel = (AbstractPanel) findComponent("loginPanel");
        JLabel infoLabel = (JLabel) panel.findComponent("infoLabel");

        Thread connectionHandler = new Thread(new ConnectionHandler(authentication, infoLabel));
        connectionHandler.start();
    }

    protected void configureFrame()
    {
        super.configureFrame();

//        setLayout(new BorderLayout());
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

        add(mainMenu, c);
        attachMenuListeners(mainMenu);

        /**
         * ------------------------------------------------
         * dummy panel to always keep the menu at the top
         * ------------------------------------------------
         */
        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;

        add(new JPanel(), c);

        /**
         * -------------
         * Login panel
         * -------------
         */
        LoginPanel loginPanel = new LoginPanel(authentication);
        loginPanel.setName("loginPanel");

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTHWEST;

        add(loginPanel, c);
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
        if(loginPanel != null) {
            remove(loginPanel);
        }

        // Repainting
        revalidate();
        repaint();
    }

    private void setupEvents()
    {
        final Main currentFrame = this;
        final AbstractPanel loginPanel = (AbstractPanel) findComponent("loginPanel");

        Connection connection = authentication.getConnection();

        // Connection events
        connection.getEndpoint().addStatusListener(new ConnectionStatusListener()
        {
            @Override
            public void onConnectionEstablished()
            {
                JButton loginBtn = (JButton) loginPanel.findComponent("loginBtn");
                loginBtn.setEnabled(true);

                JLabel infoLabel = (JLabel) loginPanel.findComponent("infoLabel");
                infoLabel.setText("<html><center>Connected to <br>messaging server</center>");
            }
        });

        // Login events
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
                    authentication.authenticate(identifier.getText(), new String(password.getPassword()));
                    currentFrame.showUserList();
                }
            }
        });
    }
}
