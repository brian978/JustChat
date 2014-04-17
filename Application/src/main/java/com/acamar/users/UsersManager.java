package com.acamar.users;

import java.util.ArrayList;

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

    public UsersManager add(User user)
    {
        users.add(user);

        for (UsersManagerListener listener : listeners) {
            listener.addedUser(user);
        }

        return this;
    }

    public UsersManager remove(User user)
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
}
