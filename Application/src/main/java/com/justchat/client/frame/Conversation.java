package com.justchat.client.frame;

import com.acamar.gui.frame.AbstractFrame;
import com.acamar.gui.menu.AbstractMenu;
import com.acamar.net.xmpp.Connection;
import com.justchat.client.frame.menu.ChatMenu;
import com.justchat.client.gui.panel.ChatPanel;
import com.justchat.client.gui.panel.ErrorPanel;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Conversation extends AbstractFrame
{
    Connection connection = null;
    String connectionMessage = null;
    Chat chat = null;

    public Conversation(Connection connection)
    {
        super("JustChat - conversation");

        this.connection = connection;

        // Creating the chat session
        ChatManager chatmanager = connection.getEndpoint().getChatManager();
        chat = chatmanager.createChat("asf", new MessageListener());

        // Setting up the new frame
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

        ChatPanel chatPanel = new ChatPanel();
        chatPanel.setName("ChatPanel");
        add(chatPanel, c);
    }

    private void setupEvents()
    {
        // Message event listeners

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

    public Chat getChat()
    {
        return chat;
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

    private class MessageListener implements org.jivesoftware.smack.MessageListener
    {
        @Override
        public void processMessage(Chat chat, Message message)
        {
            System.out.println("Received message " + message.getBody() + " from " + message.getFrom());
        }
    }
}
