package com.acamar.users;

import java.util.ArrayList;
import java.util.Collections;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class UsersManager
{
    ArrayList<User> users = new ArrayList<>();
    ArrayList<UsersManagerListener> listeners = new ArrayList<>();
    User currentUser = null;

    public synchronized UsersManager add(User user)
    {
        users.add(user);

        for (UsersManagerListener listener : listeners) {
            listener.userAdded(user);
        }

        return this;
    }

    public synchronized UsersManager remove(User user)
    {
        users.remove(user);

        for (UsersManagerListener listener : listeners) {
            listener.userRemoved(user);
        }

        return this;
    }

    public ArrayList<User> getUsers()
    {
        return users;
    }

    public User find(String id)
    {
        for (User user : users) {
            if (user.getIdentity().equals(id)) {
                return user;
            }
        }

        return null;
    }

    public User findByName(String name)
    {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Adds the users that is using the application to the manager so we can make use of this information later
     *
     * @param currentUser User that has authenticated
     */
    public void setUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public User getUser()
    {
        return currentUser;
    }

    public void sort()
    {
        Collections.sort(users);

        for (UsersManagerListener listener : listeners) {
            listener.usersSorted(users);
        }
    }

    public synchronized void removeAll()
    {
        for (User user : users) {
            for (UsersManagerListener listener : listeners) {
                listener.userRemoved(user);
            }
        }

        users.clear();
    }

    public void addListener(UsersManagerListener listener)
    {
        listeners.add(listener);
    }
}
