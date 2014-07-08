package com.justchat.mvc.view.frame;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.mvc.view.AbstractFrame;
import com.justchat.mvc.view.frame.menu.MainMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
public abstract class AbstractMainFrame extends AbstractFrame implements EventManagerAwareInterface
{
    protected EventManager eventManager = null;
    protected MainMenu menu = new MainMenu();

    /**
     * Creates the object, sets the title of the frame and makes sure that the frame is no smaller then is should
     *
     * @param title Title of the frame
     */
    public AbstractMainFrame(String title)
    {
        super(title);
    }

    /**
     * Configures, populates and loads the events for the frame
     *
     * @return AbstractMainFrame
     */
    public AbstractMainFrame initialize()
    {
        configure();
        populateFrame();
        setupEvents();

        return this;
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     */
    abstract protected void populateFrame();

    /**
     * Adds event handlers to the events that will be triggered by elements on the frame
     */
    protected void setupEvents()
    {
        /**
         * -----------------
         * Menu setup
         * ----------------
         */
        // Preferences action
        menu.findItemByName("preferencesItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                eventManager.trigger(MvcEvent.LOAD_PREFERENCES, e.getSource());
            }
        });

        // Exit action
        menu.findItemByName("exitItem").addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (menu.getParentWindow((JMenuItem) e.getSource()) == container) {
                    eventManager.trigger(new MvcEvent(MvcEvent.APPLICATION_EXIT, null));
                }
            }
        });
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
