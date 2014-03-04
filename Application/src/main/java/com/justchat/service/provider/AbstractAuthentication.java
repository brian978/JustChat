package com.justchat.service.provider;

import com.justchat.client.identity.User;
import com.justchat.client.websocket.Connection;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
abstract public class AbstractAuthentication implements AuthenticationInterface
{
    private Connection connection;
    private User user = null;

    public AbstractAuthentication(Connection connection)
    {
        this.connection = connection;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public User getUser()
    {
        return user;
    }
}
