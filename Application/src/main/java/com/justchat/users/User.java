package com.justchat.users;

import com.justchat.mvc.view.panel.components.UserCategory;

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

    /**
     * Creates a user object only with an identity
     *
     * @param identity String the identifies the user uniquely
     */
    public User(String identity)
    {
        super(identity);
    }

    /**
     * Creates a user object with both an identity and a name
     *
     * @param identity String the identifies the user uniquely
     * @param name     Name of the user that will be displayed in the GUI
     */
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
