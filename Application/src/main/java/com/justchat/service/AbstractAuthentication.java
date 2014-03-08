package com.justchat.service;

import com.justchat.model.user.identity.User;
import com.justchat.client.websocket.Connection;
import com.justchat.event.EventsManager;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
abstract public class AbstractAuthentication implements AuthenticationInterface
{
    protected EventsManager eventsManager = null;
    protected Connection connection = null;
    protected User user = null;

    public AbstractAuthentication(EventsManager eventsManager, Connection connection)
    {
        this.eventsManager = eventsManager;
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
