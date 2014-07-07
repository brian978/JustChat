package com.justchat.mvc.view.frame;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.event.EventManager;
import com.justchat.mvc.view.panel.UsersPanel;
import com.justchat.mvc.view.frame.menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-22
 */
public class Contacts extends AbstractMainFrame
{
    private UsersPanel usersPanel = new UsersPanel();

    /**
     * Creates a contacts frame object
     *
     */
    public Contacts()
    {
        super("JustChat - Contacts");
    }

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        super.setEventManager(eventManager);

        usersPanel.setEventManager(eventManager);
    }

    /**
     *
     * @return An instance of an user list panel
     */
    public UsersPanel getUsersPanel()
    {
        return usersPanel;
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     *
     */
    @Override
    protected void configure()
    {
        super.configure();

        container.setLayout(new BoxLayout(container.getContentPane(), BoxLayout.PAGE_AXIS));
    }

    /**
     * Sets the minimum size of the frame
     *
     */
    @Override
    protected void ensureMinimumSize()
    {
        container.setMinimumSize(new Dimension(200, 300));
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     *
     */
    @Override
    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        container.setJMenuBar(menu.getContainer());

        menu.display(MainMenu.DEFAULT_ITEMS);
        menu.display(MainMenu.AUTHENTICATED_ITEMS);

        /**
         * -------------
         * User list
         * -------------
         */
        container.add(usersPanel);
    }

    /**
     * Adds event handlers to the events that will be triggered by elements on the frame
     *
     */
    @Override
    protected void setupEvents()
    {
        super.setupEvents();

        /**
         * -----------------------
         * Menu handlers
         * -----------------------
         */
        // Logout action
        menu.findItemByName("logoutItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                eventManager.trigger(AuthenticationEvent.TYPE_LOGOUT, null);
            }
        });
    }
}
