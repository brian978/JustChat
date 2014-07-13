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

    /**
     * Adds a user to the users array and dispatches the userAdded event
     *
     * @param user User to be added to the list of users
     * @return UsersManager
     */
    public synchronized UsersManager add(User user)
    {
        System.out.println("Added user " + user);

        users.add(user);

        for (UsersManagerListener listener : listeners) {
            listener.userAdded(user);
        }

        return this;
    }

    /**
     * Removes a user from the users array and dispatches the userRemoved event
     *
     * @param user User to be added to the list of users
     * @return UsersManager
     */
    public synchronized UsersManager remove(User user)
    {
        users.remove(user);

        for (UsersManagerListener listener : listeners) {
            listener.userRemoved(user);
        }

        return this;
    }

    /**
     * Returns the list of users that the manager has
     *
     * @return ArrayList<User>
     */
    public ArrayList<User> getUsers()
    {
        return users;
    }

    /**
     * Searches for a user its identifier
     *
     * @param id The identity of the user that needs to be found
     * @return User
     */
    public User find(String id)
    {
        for (User user : users) {
            if (user.getIdentity().equals(id)) {
                return user;
            }
        }

        return null;
    }

    /**
     * Sorts the list of users and then dispatches a usersSorted event
     *
     */
    public void sort()
    {
        Collections.sort(users);
        for (UsersManagerListener listener : listeners) {
            listener.usersSorted(users);
        }
    }

    /**
     * Dispatches a userRemoved event and then clears the user list
     *
     */
    public synchronized void removeAll()
    {
        for (User user : users) {
            for (UsersManagerListener listener : listeners) {
                listener.userRemoved(user);
            }
        }

        users.clear();
    }

    /**
     * Attaches an event listener that will be called when a certain action is done on the user list
     *
     * @param listener Event listener object
     */
    public void addListener(UsersManagerListener listener)
    {
        listeners.add(listener);
    }
}
