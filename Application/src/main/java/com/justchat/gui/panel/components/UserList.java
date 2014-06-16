package com.justchat.gui.panel.components;

import com.acamar.users.UsersManagerListener;
import com.justchat.users.NullUser;
import com.justchat.users.User;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.ArrayList;

/**
 * JustChat
 *
 * @version 1.3
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-09
 */
public class UserList extends JTree implements UsersManagerListener
{
    private int totalUsers = 0;
    private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("All contacts");
    private DefaultTreeModel model = new DefaultTreeModel(rootNode, false);

    public UserList()
    {
        setModel(model);
        setRootVisible(true);
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // We have 2 default categories
        rootNode.add(new UserCategory("Online", UserCategory.ONLINE));
        rootNode.add(new UserCategory("Offline", UserCategory.OFFLINE));
    }

    /**
     * Locates a category by name
     *
     * @param name Category name
     * @return UserCategory
     */
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

    /**
     * Returns the user the is currently selected in the list or a null user that has no identity or name
     *
     * @return User
     */
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

    /**
     * Moves the user from one category to another
     *
     * @param user User to be updated
     * @param category New category of the user
     */
    public void updateUser(User user, UserCategory category)
    {
        if (removeUser(user)) {
            user.setCategory(category);
            addUser(user);
        }
    }

    /**
     * Adds a user to list in the users category and expands the tree path after the first user is added
     * Increments the total users count
     *
     * @param user User to be added
     */
    protected void addUser(User user)
    {
        totalUsers++;

        UserCategory category = user.getCategory();
        model.insertNodeInto(new DefaultMutableTreeNode(user), category, category.getChildCount());

        if (category.getChildCount() == 1) {
            expandPath(new TreePath(category.getPath()));
        }
    }

    /**
     * Removes a user from the list using the users category as a starting point for searching
     * Also it decrements the total users count
     *
     * @param user User to be removed
     * @return boolean
     */
    protected boolean removeUser(User user)
    {
        totalUsers--;

        UserCategory category = user.getCategory();
        DefaultMutableTreeNode node;

        // Going through the categories to remove all the users
        for (int i = 0; i < category.getChildCount(); i++) {
            node = ((DefaultMutableTreeNode) category.getChildAt(i));
            if (node.getUserObject() == user) {
                System.out.println("Removing user " + user + " from category " + category.getUserObject());
                model.removeNodeFromParent(node);

                return true;
            }
        }

        return false;
    }

    /**
     * Removes all the users from the list
     *
     */
    protected void removeAllUsers()
    {
        totalUsers = 0;

        UserCategory category;
        ArrayList<DefaultMutableTreeNode> userList = new ArrayList<>();

        // Collecting the users to be removed
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            category = ((UserCategory) rootNode.getChildAt(i));
            for (int j = 0; j < category.getChildCount(); j++) {
                userList.add((DefaultMutableTreeNode) category.getChildAt(j));
            }
        }

        // Removing the users
        for (DefaultMutableTreeNode node : userList) {
            model.removeNodeFromParent(node);
        }
    }

    /**
     * Adds a list of users to the user list
     *
     * @param list List of users to add
     */
    public void importUsers(ArrayList<com.acamar.users.User> list)
    {
        for (com.acamar.users.User user : list) {
            addUser((User) user);
        }
    }

    /**
     * Event that is triggered when a user is added in the UsersManager object
     *
     * @param user User that was added
     */
    @Override
    public void userAdded(com.acamar.users.User user)
    {

    }

    /**
     * Event that is triggered when a user will be removed from the UsersManager object
     *
     * @param user User that will be removed
     */
    @Override
    public void userRemoved(com.acamar.users.User user)
    {
        removeUser((User) user);
    }

    /**
     * Event that is triggered after the user list is sorted by the UsersManager
     *
     * @param list A sorted list of users
     */
    @Override
    public void usersSorted(ArrayList<com.acamar.users.User> list)
    {
        if (totalUsers > 0) {
            removeAllUsers();
        }

        importUsers(list);
    }
}
