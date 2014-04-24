package com.justchat.client.frame.menu;

import com.acamar.gui.swing.menu.*;
import com.acamar.gui.swing.menu.ButtonGroup;

import javax.swing.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-22
 */
public class ContactsMenu extends AbstractMenu
{
    JMenu menu = addMenu("File", "fileMenu");

    public ContactsMenu()
    {
        // Buttons that show up when the user is authenticated
        ButtonGroup authGroup = new ButtonGroup();
        authGroup.setTag("authenticated");

        // Buttons that show up all the time
        ButtonGroup defaultGroup = new ButtonGroup();
        authGroup.setTag("default");

        JMenuItem settingsBtn = createMenuItem("Preferences", "preferencesItem", defaultGroup);
        JMenuItem logoutBtn = createMenuItem("Logout", "logoutItem", authGroup);
        JMenuItem exitBtn = createMenuItem("Exit", "exitItem", defaultGroup);

        // Populating the menu
        menu.add(settingsBtn);
        menu.add(logoutBtn);
        menu.add(exitBtn);
    }
}
