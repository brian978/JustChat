package com.acamar.gui.swing.menu;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-03
 */
public abstract class AbstractMenu
{
    protected JMenuBar container = new JMenuBar();
    protected HashMap<String, JMenuItem> menuItems = new HashMap<>();

    /**
     * Creates a new top level menu that is added to the menu bar
     *
     * @param label Label of the menu entry
     * @param name  Name of the menu to identify in the code
     * @return JMenu
     */
    protected JMenu addMenu(String label, String name)
    {
        JMenu menu = new JMenu(label);
        menu.setName(name);

        return container.add(menu);
    }

    /**
     * Creates a simple menu item that is added to the menu items list
     *
     * @param label The displayed text
     * @param name  A name that can be used to identify the button in code
     * @return JMenuItem
     */
    protected JMenuItem createMenuItem(String label, String name)
    {
        JMenuItem menuItem = new JMenuItem(label);
        menuItem.setName(name);

        menuItems.put(name, menuItem);

        return menuItem;
    }

    /**
     * Creates a simple menu item and adds it to a button group
     *
     * @param label The displayed text
     * @param name  A name that can be used to identify the button in code
     * @param group A button group to add the item to
     * @return JMenuItem
     */
    protected JMenuItem createMenuItem(String label, String name, ButtonGroup group)
    {
        JMenuItem menuItem = createMenuItem(label, name);
        menuItem.setVisible(false);

        group.add(menuItem);

        return menuItem;
    }

    /**
     * Creates a menu item and adds it to the menu
     *
     * @param menu  Menu to add an item to
     * @param label Label of the item
     * @param name  A name that can be used to identify the item in code
     * @return JMenuItem
     */
    protected JMenuItem addMenuItem(JMenu menu, String label, String name)
    {
        return menu.add(createMenuItem(label, name));
    }

    /**
     * The method can only makes visible groups of buttons so if a button is not in a group then tough luck :)
     *
     * @param tagName The tag that will be used to find a group of menu items (identified via a ButtonGroup)
     */
    public void display(String tagName)
    {
        ButtonGroup group;
        Collection<JMenuItem> items = menuItems.values();

        for (JMenuItem item : items) {
            group = ((ButtonGroup) ((DefaultButtonModel) item.getModel()).getGroup());

            if (group.tag.equals(tagName)) {
                item.setVisible(true);
            }
        }
    }

    /**
     * The method can only hide groups of buttons so if a button is not in a group then tough luck :)
     *
     * @param tagName The tag that will be used to find a group of menu items (identified via a ButtonGroup)
     */
    public void hide(String tagName)
    {
        ButtonGroup group;
        Collection<JMenuItem> items = menuItems.values();

        for (JMenuItem item : items) {
            group = ((ButtonGroup) ((DefaultButtonModel) item.getModel()).getGroup());

            if (group.tag.equals(tagName)) {
                item.setVisible(false);
            }
        }
    }

    /**
     * Can be used to determine the window that holds the JMenu by using an item from it
     *
     * @param item Any random item from the menu
     * @return Window
     */
    public Window getParentWindow(JMenuItem item)
    {
        JPopupMenu popupMenu = (JPopupMenu) item.getParent();
        JMenu jMenu = (JMenu) popupMenu.getInvoker();

        return SwingUtilities.getWindowAncestor(jMenu);
    }

    /**
     * Returns the container where the menu elements are added
     *
     * @return JMenuBar
     */
    public JMenuBar getContainer()
    {
        return container;
    }

    /**
     * Finds a menu item (it can be either a top level or from a group)
     *
     * @param name The name of the menu entry
     * @return JMenuItem
     */
    public JMenuItem findItemByName(String name)
    {
        return menuItems.get(name);
    }
}
