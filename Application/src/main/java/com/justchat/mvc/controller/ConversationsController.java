package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.net.xmpp.Connection;
import com.justchat.mvc.view.frame.Conversation;
import com.justchat.users.User;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-07-12
 */
public class ConversationsController extends AbstractController
{
    private User currentUser;
    private Connection connection;
    private HashMap<User, ConversationContainer> conversations = new HashMap<>();

    /**
     * The method will be called after all the dependencies have been injected in the controller
     */
    @Override
    public void completeSetup()
    {
        if (!setupCompleted) {
            // Now that we have all the elements on the frame we need to attach some events on it
            attachEvents();
        }
    }

    /**
     * Attached the events for which the controller or a nested object will listen to
     */
    private void attachEvents()
    {
        /**
         * -----------------------
         * Event manager events
         * -----------------------
         */
        eventManager.attach(AuthenticationEvent.class, new LoginEventListener());
        eventManager.attach("new.conversation", new ConversationStartListener());
        eventManager.attach(MvcEvent.WINDOW_CLOSING, new ConversationCloseListener());
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-07-13
     */
    private class LoginEventListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         * It will update the login fields
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            AuthenticationEvent event = (AuthenticationEvent) e;

            // If the login is successful we hide the current frame (since we don't need it for now)
            if (event.getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                Authentication authentication = (Authentication) event.getParams().get("object");
                connection = authentication.getConnection();

                // Updating the current user
                currentUser = new User(event.getIdentity());
            }
        }
    }

    /**
     * The listener will create a new chat window
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-07-13
     */
    private class ConversationStartListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            // Since a new conversation is started we need the remote user to create the instance for
            User remoteUser = (User) e.getParams().get("remoteUser");

            if (!conversations.containsKey(remoteUser)) {
                // Creating the chat object
                ChatManager chatmanager = connection.getEndpoint().getChatManager();
                Chat chat = chatmanager.createChat(remoteUser.getIdentity(), null);

                // Setting up the window
                Conversation window = new Conversation();
                window.setEventManager(eventManager);
                window.initialize();

                // Creating the rest of the objects we need
                ConversationContainer container = new ConversationContainer(remoteUser, chat, window);
                InboundMessageListener inboundMessageListener = new InboundMessageListener(container);
                OutboundMessageListener outboundMessageListener = new OutboundMessageListener(container);

                // We need to do some final dependency injections
                chat.addMessageListener(inboundMessageListener);
                window.getChatPanel().getMessageBox().addKeyListener(outboundMessageListener);

                // Registering the container in the hash map so we don't create it again
                conversations.put(remoteUser, container);

                // Now when we have all the objects we can initialize the window
                window.display();
            } else {
                final ConversationContainer container = conversations.get(remoteUser);
                EventQueue.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        JFrame frame = container.getWindow().getViewContainer();
                        frame.toFront();
                        frame.repaint();
                    }
                });
            }
        }
    }

    /**
     * The listener decides what happens when a conversation is closed
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-07-13
     */
    private class ConversationCloseListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            Object target = e.getTarget();
            if (target instanceof Conversation) {
                // Removing the conversation container from the hash map
                for (ConversationContainer container : conversations.values()) {
                    if (container.getWindow() == target) {
                        conversations.remove(container.getRemoteUser());
                        break;
                    }
                }
            }
        }
    }

    /**
     * The listener is called when the user does a "send message" action
     *
     * @version 1.1
     * @link https://github.com/brian978/JustChat
     * @since 2014-02-03
     */
    private class OutboundMessageListener extends KeyAdapter implements ActionListener
    {
        private final ConversationContainer container;

        /**
         * @param container The remote user that the listener will use in the frame
         */
        public OutboundMessageListener(ConversationContainer container)
        {
            this.container = container;
        }

        /**
         * The method is used to send a message after an action has been performed
         * (like hitting enter or pressing the send button)
         *
         * @param e Event that was triggered from the chat box
         */
        private void sendFieldContents(AWTEvent e)
        {
            JTextField field = (JTextField) e.getSource();
            String message = field.getText();
            field.setText("");

            if (message.length() > 0) {
                container.getWindow()
                         .getChatPanel()
                         .getChatBox()
                         .append(Color.RED, currentUser, message);

                try {
                    container.getChat().sendMessage(message);
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
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == 10) {
                sendFieldContents(e);
            }
        }
    }

    /**
     * The listener is called when the user receives a message from a "remoteUser"
     *
     * @version 1.1
     * @link https://github.com/brian978/JustChat
     * @since 2014-02-03
     */
    private class InboundMessageListener implements MessageListener
    {
        private final ConversationContainer container;

        /**
         * @param container The remote user that the listener will use in the frame
         */
        public InboundMessageListener(ConversationContainer container)
        {
            this.container = container;
        }

        @Override
        public void processMessage(Chat chat, Message message)
        {
            // For now we ignore notification messages
            String messageBody = message.getBody();
            if (messageBody != null) {
                container.getWindow()
                         .getChatPanel()
                         .getChatBox()
                         .append(Color.BLUE, container.getRemoteUser(), message.getBody());
            }
        }
    }

    /**
     * An object is created from this class each time a new conversation window needs to be created
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-02-03
     */
    private class ConversationContainer
    {
        private final User remoteUser;
        private final Chat chat;
        private final Conversation window;

        /**
         * @param remoteUser The user that the conversation will be initiated with
         * @param chat       Chat object so we can identify
         * @param window     Conversation frame
         */
        public ConversationContainer(User remoteUser, Chat chat, Conversation window)
        {
            this.remoteUser = remoteUser;
            this.chat = chat;
            this.window = window;
        }

        /**
         * @return User object for the conversation
         */
        public User getRemoteUser()
        {
            return remoteUser;
        }

        /**
         * @return Chat object from Smack API
         */
        public Chat getChat()
        {
            return chat;
        }

        /**
         * @return Frame object that holds all the elements for the chat window
         */
        public Conversation getWindow()
        {
            return window;
        }
    }
}
