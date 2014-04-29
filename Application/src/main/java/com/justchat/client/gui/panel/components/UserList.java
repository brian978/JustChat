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

    @Override
    public void addedUser(User user) throws NullPointerException
    {
        user.getCategory().add(new DefaultMutableTreeNode(user));

        expandPath(new TreePath(user.getCategory().getPath()));
    }

    @Override
    public void removedUser(User user)
    {
        // TODO: implement remove user functionality
    }

    public void removeAllElements()
    {
        // Going through the categories to remove all the users
        for(int i = 0; i < rootNode.getChildCount(); i++){
            ((UserCategory) rootNode.getChildAt(i)).removeAllChildren();
        }
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

        if (node != null && node.isLeaf() && !(node.getUserObject() instanceof String)) {
            user = (User) node.getUserObject();
        } else {
            user = new NullUser();
        }

        return user;
    }
}
