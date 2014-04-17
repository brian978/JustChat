package com.acamar.users;

import java.util.ArrayList;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class UserList
{
    ArrayList<User> users = new ArrayList<>();
    ArrayList<UserListListener> listeners = new ArrayList<>();

    public UserList add(User user)
    {
        users.add(user);

        for (UserListListener listener : listeners) {
            listener.addedUser(user);
        }

        return this;
    }

    public UserList remove(User user)
    {
        users.remove(user);

        for (UserListListener listener : listeners) {
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
}
