package com.justchat.client.frame;

import com.acamar.event.EventManager;
import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.menu.AbstractMenu;
import com.acamar.websocket.AsyncConnection;
import com.acamar.websocket.Connection;
import com.acamar.websocket.SocketMessageListener;
import com.justchat.client.frame.menu.ChatMenu;
import com.justchat.client.gui.panel.ChatPanel;
import com.justchat.client.gui.panel.ErrorPanel;
import com.justchat.model.user.identity.User;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Conversation extends AbstractFrame
{
    User user;
    Connection connection = null;
    String connectionMessage = null;
    SocketMessageListener messageListener = new MessageListener();

    public Conversation(Connection connection)
    {
        super("JustChat - conversation");

        user = new User("adsad", "Current user", true);

        this.connection = connection;

        configureFrame();
        populateFrame();
        setupEvents();
        showFrame();
        ensureMinimumSize();
    }

    @Override
    protected void configureFrame()
    {
        super.configureFrame();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
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
        c.weightx = 1.0;
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

    private void setupEvents()
    {
        // Message event listeners
        EventManager.add(SocketMessageListener.class, messageListener);

        // Frame events
        addWindowListener(new CleanupWindowListener());
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

    protected void attachMenuListeners(AbstractMenu menu)
    {
        final JFrame currentFrame = this;
        JMenuItem item;

        /**
         * -----------------------
         * Listener for send file
         * -----------------------
         */
        item = menu.findItemByName("sendFileItem");
        if (item != null) {
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
         * -------------------------------
         * Listener for close conversation
         * -------------------------------
         */
        item = menu.findItemByName("conversationCloseItem");
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

    private class CleanupWindowListener implements WindowListener
    {
        @Override
        public void windowOpened(WindowEvent e)
        {

        }

        @Override
        public void windowClosing(WindowEvent e)
        {
            System.out.println("Cleaning up the frame");

            // Removing the event listeners
            EventManager.remove(SocketMessageListener.class, messageListener);
        }

        @Override
        public void windowClosed(WindowEvent e)
        {

        }

        @Override
        public void windowIconified(WindowEvent e)
        {

        }

        @Override
        public void windowDeiconified(WindowEvent e)
        {

        }

        @Override
        public void windowActivated(WindowEvent e)
        {

        }

        @Override
        public void windowDeactivated(WindowEvent e)
        {

        }
    }

    private class MessageListener implements SocketMessageListener
    {
        @Override
        public void processMessage(String message)
        {
            System.out.println("Received message: " + message);
        }
    }
}
