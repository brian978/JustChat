package com.acamar.gui.menu;

import javax.swing.*;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 03-03-2014
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
