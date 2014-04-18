package com.justchat.client.gui.panel;

import com.acamar.gui.panel.AbstractPanel;
import com.acamar.users.User;
import com.justchat.client.frame.Conversation;
import com.justchat.client.gui.panel.components.ChatBox;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class ChatPanel extends AbstractPanel
{
    ChatBox chatBox;
    JTextField messageBox;

    public ChatPanel()
    {
        super();

        populate();
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
        chatBox.setName("ChatTextPane");

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
        messageBox = new JTextField();
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
    }

    public ChatBox getChatBox()
    {
        return chatBox;
    }

    public JTextField getMessageBox()
    {
        return messageBox;
    }
}
