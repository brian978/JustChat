package com.justchat.client.websocket.factory;

import com.justchat.client.gui.exception.FailedToLoadConfigurationException;
import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.config.ConnectionConfig;

import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionFactory
{
    public static Connection factory() throws FailedToLoadConfigurationException
    {
        ConnectionConfig config;

        try {
            config = new ConnectionConfig();
        } catch (IOException e) {
            throw new FailedToLoadConfigurationException(e.getMessage(), e.getCause());
        }

        return new Connection(
                config.get("host"),
                Integer.parseInt(config.get("port"))
        );
    }
}
