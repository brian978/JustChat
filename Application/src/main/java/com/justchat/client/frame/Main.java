package com.justchat.client.frame;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.service.websocket.ConnectionHandler;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.factory.ConnectionFactory;
import com.justchat.gui.frame.AbstractFrame;
import com.justchat.gui.menu.AbstractMenu;
import com.justchat.client.frame.menu.MainMenu;
import com.justchat.client.gui.panel.LoginPanel;
import com.justchat.client.identity.User;
import com.justchat.service.provider.AuthenticationInterface;
import com.justchat.client.service.provider.facebook.Authentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Main extends AbstractFrame
{
    AuthenticationInterface authentication = null;
    User user = null;

    public Main()
    {
        super("JustChat");

        try {
            authentication = new Authentication(ConnectionFactory.factory());
        } catch (FailedToLoadConfigurationException e) {
            e.printStackTrace();
        }

        configureFrame();
        populateFrame();
        showFrame();
        ensureMinimumSize();
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(new Dimension(200, 500));
    }

    @Override
    protected void populateFrame()
    {
        GridBagConstraints c;

        /**
         * -------------
         * main menu
         * -------------
         */
        MainMenu mainMenu = new MainMenu();

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(mainMenu, c);
        attachMenuListeners(mainMenu);

        /**
         * -------------
         * Login panel
         * -------------
         */
        LoginPanel loginPanel = new LoginPanel(authentication);

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.LINE_START;

        add(loginPanel, c);
    }

    protected void attachMenuListeners(AbstractMenu menu)
    {
        final JFrame currentFrame = this;
        JMenuItem item;

        /**
         * -----------------------
         * Listener exit command
         * -----------------------
         */
        item = menu.findItemByName("exitItem");
        if(item != null) {
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
}
