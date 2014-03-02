package com.justchat.client.gui.panel;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.SocketConfiguration;
import com.justchat.client.websocket.listeners.NewMessageListener;
import com.justchat.gui.panel.AbstractPanel;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ChatPanel extends AbstractPanel
{
    Connection connection;
    ChatBoxPanel chatBox;

    public ChatPanel() throws IOException, DeploymentException
    {
        super();

        SocketConfiguration config;

        try {
            config = new SocketConfiguration();
        } catch (IOException e) {
            throw new FailedToLoadConfigurationException("Cannot load configuration file", e.getCause());
        }

        connection = new Connection(
                config.get("host"),
                Integer.parseInt(config.get("port"))
        );

        connection.connect();

        connection.getEndpoint().setMessageListener(new NewMessageListener()
        {
            @Override
            public void onNewMessage(String message)
            {
                chatBox.append(Color.BLUE, "Some user", message);
            }
        });

        configure();
        populate();
        setupEvents();
    }

    @Override
    protected void configure()
    {
        super.configure();
    }

    @Override
    protected void populate()
    {
        super.populate();

        GridBagConstraints c;

        // Adding the Chat box where the messages will appear
        chatBox = new ChatBoxPanel();

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(chatBox.getScrollable(), c);

        // Adding the text box
        final JTextField messageBox = new JTextField();
        messageBox.setName("MessageBox");
        messageBox.setPreferredSize(new Dimension(300, 30));
        messageBox.setMaximumSize(messageBox.getPreferredSize());
        messageBox.addKeyListener(new SendListener(chatBox));

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;

        add(messageBox, c);
    }

    protected void setupEvents()
    {
    }

    private class SendListener implements KeyListener, ActionListener
    {

        private ChatBoxPanel chatBoxPanel;

        public SendListener(ChatBoxPanel chatBox)
        {
            chatBoxPanel = chatBox;
        }

        private void sendFieldContents(AWTEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            String message = field.getText();
            field.setText("");

            if (message.length() > 0) {
                chatBoxPanel.append(Color.RED, "Me", message);
                connection.sendMessage(message);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getActionCommand().equals("send")) {
                sendFieldContents(e);
            }
        }

        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == 10) {
                sendFieldContents(e);
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }
}
