package com.justchat.client.frame.menu;

import com.justchat.gui.menu.AbstractMenu;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class MainMenu extends AbstractMenu
{
    public MainMenu()
    {
        JMenu menu = addMenu("File", "fileMenu");

        addMenuItem(menu, "Preferences", "preferencesItem");
        menu.addSeparator();
        addMenuItem(menu, "Exit", "exitItem");
    }
}
