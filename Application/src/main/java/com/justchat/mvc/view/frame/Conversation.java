package com.justchat.mvc.view.frame;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.gui.swing.menu.AbstractMenu;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.mvc.view.AbstractFrame;
import com.justchat.mvc.view.frame.menu.ChatMenu;
import com.justchat.mvc.view.panel.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Conversation extends AbstractFrame implements EventManagerAwareInterface
{
    private EventManager eventManager;
    private ChatPanel chatPanel;

    /**
     * Constructor
     *
     * Creates an Conversation object
     */
    public Conversation()
    {
        super("JustChat - conversation");
    }

    /**
     * Since the main component of this frame is the chat panel we need a quick way to access it
     *
     * @return ChatPanel object
     */
    public ChatPanel getChatPanel()
    {
        return chatPanel;
    }

    /**
     * Configures, populates and loads the events for the frame
     */
    public void initialize()
    {
        super.initialize();

        // Setting up the new frame
        populateFrame();
        ensureMinimumSize();
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     */
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
        container.add(chatPanel.getViewContainer(), c);
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
        final Conversation _this = this;

        item = menu.findItemByName("conversationCloseItem");
        if (item != null) {
            item.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    eventManager.trigger(new MvcEvent(MvcEvent.WINDOW_CLOSING, _this));
                }
            });
        }
    }

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager()
    {
        return eventManager;
    }
}
