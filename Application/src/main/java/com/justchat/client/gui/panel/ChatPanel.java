package com.justchat.client.gui.panel;

import com.acamar.gui.panel.AbstractPanel;
import com.acamar.websocket.Connection;
import com.justchat.client.gui.panel.components.ChatBox;
import com.justchat.model.user.identity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    ChatBox chatBox;
    User currentUser;

    public ChatPanel(Connection connection, User user)
    {
        super();

        this.connection = connection;
        this.currentUser = user;

        populate();
        setupEvents();
    }

    protected void populate()
    {
        GridBagConstraints c;

        /**
         * -----------------
         * chat box pane
         * -----------------
         */
        chatBox = new ChatBox();

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(chatBox.getScrollable(), c);

        /**
         * -----------------
         * chat message box
         * -----------------
         */
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
        private ChatBox chatBoxPanel;

        public SendListener(ChatBox chatBox)
        {
            chatBoxPanel = chatBox;
        }

        private void sendFieldContents(AWTEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            String message = field.getText();
            field.setText("");

            if (message.length() > 0) {
                chatBoxPanel.append(Color.RED, currentUser, message);
                connection.send(message);
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
