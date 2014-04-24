package com.acamar.gui.swing.menu;

import javax.swing.*;
import java.util.Collection;
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

    protected JMenuItem createMenuItem(String label, String name)
    {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setName(name);

        menuItems.put(name, menuItem);

        return menuItem;
    }

    protected JMenuItem createMenuItem(String label, String name, ButtonGroup group)
    {
        JMenuItem menuItem = createMenuItem(label, name);
        menuItem.setVisible(false);

        group.add(menuItem);

        return menuItem;
    }

    protected JMenuItem addMenuItem(JMenu menu, String label, String name)
    {
        return menu.add(createMenuItem(label, name));
    }

    public void display(String tagName)
    {
        ButtonGroup group;
        Collection<JMenuItem> items = menuItems.values();

        for (JMenuItem item : items) {
            group = ((ButtonGroup) ((DefaultButtonModel) item.getModel()).getGroup());

            if(group.tag.equals(tagName)){
                item.setVisible(true);
            }
        }
    }

    public void hide(String tagName)
    {
        ButtonGroup group;
        Collection<JMenuItem> items = menuItems.values();

        for (JMenuItem item : items) {
            group = ((ButtonGroup) ((DefaultButtonModel) item.getModel()).getGroup());

            if(group.tag.equals(tagName)){
                item.setVisible(false);
            }
        }
    }

    public JMenuItem findItemByName(String name)
    {
        return menuItems.get(name);
    }
}
