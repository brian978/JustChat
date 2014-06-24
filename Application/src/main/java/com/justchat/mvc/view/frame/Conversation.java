package com.justchat.mvc.view.frame;

import com.acamar.mvc.view.AbstractFrame;
import com.acamar.gui.swing.menu.AbstractMenu;
import com.acamar.net.xmpp.Connection;
import com.justchat.users.User;
import com.justchat.mvc.view.frame.menu.ChatMenu;
import com.justchat.mvc.view.panel.ChatPanel;
import com.justchat.mvc.view.panel.components.ChatBox;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

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
public class Conversation extends AbstractFrame
{
    private User localUser;
    private User remoteUser;
    private Chat chat;
    private ChatPanel chatPanel;

    public Conversation(Connection connection, User remoteUser)
    {
        super("JustChat - conversation");

        this.remoteUser = remoteUser;

        // Creating the chat session
        ChatManager chatmanager = connection.getEndpoint().getChatManager();
        chat = chatmanager.createChat(remoteUser.getIdentity(), new InboundMessageListener());

        // Setting up the new frame
        configure();
        populateFrame();
        setupEvents();
        display();
        ensureMinimumSize();
    }

    public void setLocalUser(User localUser)
    {
        this.localUser = localUser;
    }

    @Override
    protected void configure()
    {
        super.configure();

        container.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    protected void populateFrame()
    {
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

        container.add(menu.getContainer(), c);
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

        chatPanel = new ChatPanel();
        chatPanel.setName("ChatPanel");
        container.add(chatPanel, c);
    }

    private void setupEvents()
    {
        // Message event listeners
        chatPanel.getMessageBox().addKeyListener(new OutboundMessageListener(chatPanel.getChatBox()));

        // Frame events
        container.addWindowListener(new CleanupWindowListener());
    }

    protected void attachMenuListeners(AbstractMenu menu)
    {
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
                    container.dispatchEvent(new WindowEvent(container, WindowEvent.WINDOW_CLOSING));
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

    private class OutboundMessageListener implements KeyListener, ActionListener
    {
        private ChatBox chatPane;

        public OutboundMessageListener(ChatBox chatBox)
        {
            chatPane = chatBox;
        }

        private void sendFieldContents(AWTEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            String message = field.getText();
            field.setText("");

            if (message.length() > 0) {
                chatPane.append(Color.RED, localUser, message);

                try {
                    chat.sendMessage(message);
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

    private class InboundMessageListener implements MessageListener
    {
        @Override
        public void processMessage(Chat chat, Message message)
        {
            // For now we ignore notification messages
            String messageBody = message.getBody();
            if (messageBody != null) {
                chatPanel.getChatBox().append(Color.BLUE, remoteUser, message.getBody());
            }
        }
    }
}