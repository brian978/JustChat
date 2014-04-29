package com.justchat.client.gui.panel.components;

import com.acamar.users.NullUser;
import com.acamar.users.User;
import com.acamar.users.UsersManagerListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;

/**
 * JustChat
 *
 * @version 1.2
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-09
 */
public class UserList extends JTree implements UsersManagerListener
{
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("All contacts");

    public UserList()
    {
        setModel(new DefaultTreeModel(rootNode, false));
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void addedUser(User user)
    {
        rootNode.add(new DefaultMutableTreeNode(user));
    }

    @Override
    public void removedUser(User user)
    {
        //TODO: implement remove user functionality
    }

    public void removeAllElements()
    {
        rootNode.removeAllChildren();
    }

    @Override
    public void sortComplete(ArrayList<User> list)
    {
        removeAllElements();
        for (User user : list) {
            addedUser(user);
        }
    }

    public User getSelectedUser()
    {
        User user;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();

        if (node != null && node.isLeaf()) {
            user = (User) node.getUserObject();
        } else {
            user = new NullUser();
        }

        return user;
    }
}
