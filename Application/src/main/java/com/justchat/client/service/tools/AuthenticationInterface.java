package com.justchat.client.service.tools;

import com.justchat.client.identity.User;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface AuthenticationInterface
{
    public User authenticate(String identifier, String password);
}
