package com.acamar.users;

import com.justchat.client.gui.panel.components.UserCategory;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public interface UserInterface
{
    public void setState(User.UserState state);

    public String getIdentity();

    public String getName();

    public UserCategory getCategory();

    public void setCategory(UserCategory category);

    public User.UserState getState();
}
