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
            listener.addedUser(user);
        }

        return this;
    }

    public synchronized UsersManager remove(User user)
    {
        users.remove(user);

        for (UsersManagerListener listener : listeners) {
            listener.removedUser(user);
        }

        return this;
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

    public void addListener(UsersManagerListener listener)
    {
        listeners.add(listener);
    }

    public User getCurrentUser()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public void sort()
    {
        Collections.sort(users);

        for (UsersManagerListener listener : listeners) {
            listener.sortComplete(users);
        }
    }

    public synchronized void removeAll()
    {
        for (User user : users) {
            for (UsersManagerListener listener : listeners) {
                listener.removedUser(user);
            }
        }

        users.clear();
    }
}
