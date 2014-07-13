package com.justchat.mvc.view.panel.components;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-29
 */
public class UserCategory extends DefaultMutableTreeNode
{
    final public static int ONLINE = 1;
    final public static int OFFLINE = 2;

    protected int categoryType;

    /**
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param userObject an Object provided by the user that constitutes
     *                   the node's data
     */
    public UserCategory(Object userObject, int categoryType)
    {
        super(userObject);

        this.categoryType = categoryType;
    }
}
