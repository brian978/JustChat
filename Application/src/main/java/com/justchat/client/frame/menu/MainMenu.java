package com.justchat.client.frame.menu;

import com.acamar.gui.swing.menu.*;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @version 1.1
 * @since 2014-03-03
 */
public class MainMenu extends AbstractMenu
{
    final public static String DEFAULT_ITEMS = "default";
    final public static String AUTHENTICATED_ITEMS = "authenticated";

    JMenu menu = addMenu("File", "fileMenu");

    public MainMenu()
    {
        // Buttons that show up all the time
        com.acamar.gui.swing.menu.ButtonGroup defaultGroup = new com.acamar.gui.swing.menu.ButtonGroup();
        defaultGroup.setTag("default");

        // Buttons that show up when the user is authenticated
        com.acamar.gui.swing.menu.ButtonGroup authGroup = new com.acamar.gui.swing.menu.ButtonGroup();
        authGroup.setTag("authenticated");

        // Creating our buttons
        JMenuItem settingsBtn = createMenuItem("Preferences", "preferencesItem", defaultGroup);
        JMenuItem logoutBtn = createMenuItem("Logout", "logoutItem", authGroup);
        JMenuItem exitBtn = createMenuItem("Exit", "exitItem", defaultGroup);

        // Populating the menu
        menu.add(settingsBtn);
        menu.add(logoutBtn);
        menu.add(exitBtn);
    }
}
