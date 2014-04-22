package com.justchat.client.frame.menu;

import com.acamar.gui.menu.AbstractMenu;

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
    public ContactsMenu()
    {
        JMenu menu = addMenu("File", "fileMenu");

        addMenuItem(menu, "Preferences", "preferencesItem");
        menu.addSeparator();
        addMenuItem(menu, "Logout", "logoutItem");
        menu.addSeparator();
        addMenuItem(menu, "Exit", "exitItem");
    }
}
