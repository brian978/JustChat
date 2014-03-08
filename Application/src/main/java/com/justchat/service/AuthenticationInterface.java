package com.justchat.service;

import com.justchat.model.user.identity.User;
import com.justchat.client.websocket.Connection;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface AuthenticationInterface
{
    public boolean authenticate(String identifier, char[] password);

    public Connection getConnection();

    public User getUser();
}
