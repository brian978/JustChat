package com.justchat.client.gui.panel.components;

import com.acamar.users.NullUser;
import com.acamar.users.User;
import com.acamar.users.UsersManagerListener;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
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
    private int totalUsers = 0;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("All contacts");

    public UserList()
    {
        DefaultTreeModel model = new DefaultTreeModel(rootNode, false);

        setModel(model);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // We have 2 default categories
        rootNode.add(new UserCategory("Online", UserCategory.ONLINE));
        rootNode.add(new UserCategory("Offline", UserCategory.OFFLINE));

        model.reload(rootNode);
    }

    public UserCategory findCategory(String name)
    {
        UserCategory category;
        int count = rootNode.getChildCount();

        for (int i = 0; i < count; i++) {
            category = (UserCategory) rootNode.getChildAt(i);
            if (category.getUserObject().equals(name)) {
                return category;
            }
        }

        return null;
    }

    public User getSelectedUser()
    {
        User user;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();

        if (node != null && node.isLeaf() && !(node.getUserObject() instanceof String)) {
            user = (User) node.getUserObject();
        } else {
            user = new NullUser();
        }

        return user;
    }

    public void addUser(User user)
    {
        totalUsers++;

        UserCategory category = user.getCategory();
        category.add(new DefaultMutableTreeNode(user));

        if(category.getChildCount() == 1) {
            expandPath(new TreePath(category.getPath()));
        }
    }

    public void removeUser(User user)
    {
        totalUsers--;

        UserCategory category = user.getCategory();

        // Going through the categories to remove all the users
        for (int i = 0; i < category.getChildCount(); i++) {
            ((DefaultMutableTreeNode) category.getChildAt(i)).removeFromParent();
        }
    }

    public void removeAllUsers()
    {
        totalUsers = 0;

        // Going through the categories to remove all the users
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            ((UserCategory) rootNode.getChildAt(i)).removeAllChildren();
        }
    }

    public void importUsers(ArrayList<User> list)
    {
        for (User user : list) {
            addUser(user);
        }
    }

    @Override
    public void userAdded(User user)
    {
        addUser(user);
    }

    @Override
    public void userRemoved(User user)
    {
        removeUser(user);
    }

    @Override
    public void usersSorted(ArrayList<User> list)
    {
        if(totalUsers > 0) {
            removeAllUsers();
        }

        importUsers(list);
    }
}
