package com.justchat.client.service.provider.facebook;

import com.justchat.client.identity.User;
import com.justchat.client.service.tools.AbstractAuthentication;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Authentication extends AbstractAuthentication
{
    @Override
    public User authenticate(String identifier, String password)
    {
        if(identifier.length() > 0 && password.length() > 5) {
            System.out.println("Authenticating user with identifier " + identifier + " and password " + password);
        }

        return null;
    }
}
