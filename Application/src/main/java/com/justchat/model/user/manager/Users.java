package com.justchat.model.user.manager;

import com.justchat.model.user.identity.User;
import com.justchat.model.user.manager.observer.UsersActionsObserver;

import java.util.ArrayList;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Users
{
    ArrayList<UsersActionsObserver> observers = new ArrayList<>();
    ArrayList<User> users = new ArrayList<>();

    public Users()
    {
    }

    public void addObserver(UsersActionsObserver observer)
    {
        observers.add(observer);
    }

    public Users add(User user)
    {
        users.add(user);

        // Notifying the observers that a user has been added
        for(UsersActionsObserver observer : observers) {
            observer.addedUser(user);
        }

        return this;
    }

    public Users remove(User user)
    {
        users.remove(user);

        // Notifying the observers that a user has been removed
        for(UsersActionsObserver observer : observers) {
            observer.removeduser(user);
        }

        return this;
    }

    public User find(String id)
    {
        for (User usr : users) {
            if (usr.getId().equals(id)) {
                return usr;
            }
        }

        return null;
    }
}
