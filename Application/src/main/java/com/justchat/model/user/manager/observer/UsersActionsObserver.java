package com.justchat.model.user.manager.observer;

import com.justchat.model.user.identity.User;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface UsersActionsObserver
{
    public void addedUser(User user);
    public void removeduser(User user);
}
