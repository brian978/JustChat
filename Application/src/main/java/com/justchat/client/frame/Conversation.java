package com.justchat.client.frame;

import com.justchat.client.frame.menu.ChatMenu;
import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.gui.panel.ChatPanel;
import com.justchat.client.gui.panel.ErrorPanel;
import com.justchat.client.identity.User;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.factory.ConnectionFactory;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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

    protected void showErrorPanel()
    {
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        ErrorPanel errorPanel = new ErrorPanel(connectionMessage);
        add(errorPanel, c);
    }

    protected void populateFrame()
    {
        if (connectionMessage != null) {
            showErrorPanel();
            return;
        }

        GridBagConstraints c;

        /**
         * -----------------
         * menu
         * -----------------
         */
        ChatMenu menu = new ChatMenu();

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(menu, c);
        attachMenuListeners(menu);

        /**
         * -----------------
         * chat panel
         * -----------------
         */
        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        ChatPanel chatPanel = new ChatPanel(connection, user);
        chatPanel.setName("ChatPanel");
        add(chatPanel, c);
    }

    protected void attachMenuListeners(ChatMenu menu)
    {
        final JFrame currentFrame = this;
        JMenuItem item;

        /**
         * -----------------------
         * Listener for send file
         * -----------------------
         */
        item = menu.findItemByName("sendFileItem");
        if(item != null) {
            item.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // Currently we do nothing
                }
            });
        }

        /**
         * -----------------------
         * Listener for send file
         * -----------------------
         */
        item = menu.findItemByName("conversationCloseItem");
        if(item != null) {
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
}
