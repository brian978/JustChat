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
        messageBox.addKeyListener(new SendListener(this, chatBox));

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

    private class SendListener implements KeyListener, ActionListener
    {
        private ChatBox chatBoxPanel;
        private ChatPanel panel;

        public SendListener(ChatPanel chatPanel, ChatBox chatBox)
        {
            panel = chatPanel;
            chatBoxPanel = chatBox;
        }

        private void sendFieldContents(AWTEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            String message = field.getText();
            field.setText("");

            if (message.length() > 0) {
                chatBoxPanel.append(Color.RED, new User("someone", "test"), message);

                try {
                    ((Conversation) SwingUtilities.getWindowAncestor(panel)).getChat().sendMessage(message);
                } catch (XMPPException e1) {
                    e1.printStackTrace();
                }
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
