package com.justchat.view.frame;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationAwareInterface;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.mvc.view.AbstractFrame;
import com.acamar.util.Properties;
import com.justchat.view.frame.menu.MainMenu;

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
public abstract class AbstractMainFrame extends AbstractFrame implements AuthenticationAwareInterface
{
    protected MainMenu menu;
    protected Properties settings;
    protected Authentication xmppAuthentication;

    public AbstractMainFrame(String title, Properties settings)
    {
        super(title);

        this.settings = settings;
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     *
     */
    abstract protected void populateFrame();

    /**
     * Adds event handlers to the events that will be triggered by elements on the frame
     *
     */
    protected void setupEvents()
    {
        /**
         * -----------------
         * Menu setup
         * ----------------
         */
        // Preferences action
        menu.findItemByName("preferencesItem").addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        System.out.println("Launching preferences window from " + container.getTitle());
                    }
                }
        );

        // Exit action
        menu.findItemByName("exitItem").addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (menu.getParentWindow((JMenuItem) e.getSource()) == container) {
                            triggerClosingEvent();
                        }
                    }
                }
        );
    }

    public AbstractMainFrame initialize()
    {
        configure();
        populateFrame();
        setupEvents();

        return this;
    }

    @Override
    public AbstractMainFrame setAuthentication(AbstractAuthentication authentication)
    {
        this.xmppAuthentication = (Authentication) authentication;

        return this;
    }

    @Override
    public Authentication getAuthentication()
    {
        return xmppAuthentication;
    }

    public void setMenu(MainMenu menu)
    {
        this.menu = menu;
    }
}
