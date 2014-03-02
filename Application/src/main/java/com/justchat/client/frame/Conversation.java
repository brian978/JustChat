package com.justchat.client.frame;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.gui.panel.ChatPanel;
import com.justchat.client.gui.panel.ErrorPanel;
import com.justchat.client.identity.User;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.factory.ConnectionFactory;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Conversation extends JFrame
{
    User user;
    private Connection connection = null;
    String connectionMessage = null;

    public Conversation()
    {
        super("JustChat - conversation");

        user = new User("Current user", true);

        try {
            connection = ConnectionFactory.factory();
            connection.connect();
        } catch (IOException | DeploymentException e) {
            connectionMessage = e.getMessage();
        }

        configureFrame();
        populateFrame();
        showFrame();
        ensureMinimumSize();
    }

    protected void configureFrame()
    {
        // Configuring the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    protected void showFrame()
    {
        // Activating the frame
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        validate();
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(getSize());
    }

    protected void populateFrame()
    {
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        // Adding the appropriate panels
        if(connectionMessage == null) {
            ChatPanel chatPanel = new ChatPanel(connection, user);
            chatPanel.setName("ChatPanel");
            add(chatPanel, c);
        } else {
            ErrorPanel errorPanel = new ErrorPanel(connectionMessage);
            add(errorPanel, c);
        }
    }
}
