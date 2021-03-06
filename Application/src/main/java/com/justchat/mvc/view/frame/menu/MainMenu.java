package com.justchat.mvc.view.frame.menu;

import com.acamar.gui.swing.menu.AbstractMenu;
import com.acamar.gui.swing.menu.ButtonGroup;

import javax.swing.*;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
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
        ButtonGroup defaultGroup = new ButtonGroup("default");

        // Buttons that show up when the user is authenticated
        ButtonGroup authGroup = new ButtonGroup("authenticated");

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
