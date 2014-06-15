package com.justchat.client.users;

import com.justchat.client.gui.panel.components.UserCategory;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-17
 */
public class User extends com.acamar.users.User
{
    private UserCategory category = null;

    public User(String identity)
    {
        super(identity);
    }

    public User(String identity, String name)
    {
        super(identity, name);
    }

    /**
     * Returns the category object that contains the user
     *
     * @return UserCategory
     */
    public UserCategory getCategory()
    {
        return category;
    }

    /**
     * Sets the category of the user
     *
     * @param category Category that the user resides in
     */
    public void setCategory(UserCategory category)
    {
        this.category = category;
    }
}
