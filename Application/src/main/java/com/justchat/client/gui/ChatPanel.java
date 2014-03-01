package com.justchat.client.gui;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.websocket.Connection;
import com.justchat.gui.panel.AbstractPanel;
import com.justchat.util.PropertiesHandler;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
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

    public ChatPanel() throws IOException, DeploymentException
    {
        super();

        String propertiesPath = getClass().getResource("../config/client.properties").getPath();
        PropertiesHandler clientProperties = new PropertiesHandler(propertiesPath);

        try {
            clientProperties.load();
        } catch (IOException e) {
            throw new FailedToLoadConfigurationException(e.getCause());
        }

        connection = new Connection(
                clientProperties.getProperty("host"),
                Integer.parseInt(clientProperties.getProperty("port"))
        );

        connection.connect();

        configure();
        populate();
        setupEvents();
    }

    protected void setupEvents()
    {
    }

    @Override
    protected void populate()
    {
        super.populate();

        GridBagConstraints c;

        // Adding the Chat box where the messages will appear
        JTextArea chatBox = new JTextArea();
        chatBox.setName("ChatBox");
        chatBox.setLineWrap(true);
        chatBox.setPreferredSize(new Dimension(300, 300));
        chatBox.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(chatBox);

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(scrollPane, c);

        // Adding the text box
        final JTextField messageBox = new JTextField();
        messageBox.setName("MessageBox");
        messageBox.setPreferredSize(new Dimension(300, 30));
        messageBox.setMaximumSize(messageBox.getPreferredSize());

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.LINE_START;

        add(messageBox, c);

        final Connection conn = connection;

        messageBox.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {

            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == 10) {
                    connection.sendMessage(messageBox.getText());
                    messageBox.setText("");
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {

            }
        });
    }

    @Override
    protected void configure()
    {
        super.configure();
    }
}
