package com.acamar.users;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-29
 */
public class UserCategory extends DefaultMutableTreeNode
{


    /**
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     */
    public UserCategory()
    {
    }

    /**
     * Creates a tree node with no parent, no children, but which allows
     * children, and initializes it with the specified user object.
     *
     * @param userObject an Object provided by the user that constitutes
     *                   the node's data
     */
    public UserCategory(Object userObject)
    {
        super(userObject);
    }
}
