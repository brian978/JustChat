package com.acamar.gui.menu;

import javax.swing.*;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class AbstractMenu extends JMenuBar
{
    protected HashMap<String, JMenuItem> menuItems = new HashMap<>();

    protected JMenu addMenu(String label, String name)
    {
        JMenu menu = new JMenu(label);
        menu.setName(name);

        return add(menu);
    }

    protected JMenuItem addMenuItem(JMenu menu, String label, String name)
    {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setName(name);

        menuItems.put(name, menuItem);

        return menu.add(menuItem);
    }

    public JMenuItem findItemByName(String name)
    {
        return menuItems.get(name);
    }
}
