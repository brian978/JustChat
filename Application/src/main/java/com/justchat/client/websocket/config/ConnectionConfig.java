package com.justchat.client.websocket.config;

import com.justchat.util.Properties;

import java.io.File;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionConfig
{
    File config = new File("socket.properties");
    Properties properties = new Properties(config);

    public ConnectionConfig() throws IOException
    {
        this(true);
    }

    public ConnectionConfig(boolean autoload) throws IOException
    {
        if(!createFile() && autoload) {
            load();
        }
    }

    private boolean createFile() throws IOException
    {
        boolean fileCreated = false;

        if (!config.exists()) {
            if(config.createNewFile()) {
                properties.setProperty("host", "brian.hopto.org");
                properties.setProperty("port", "7896");
                properties.store();
                fileCreated = true;
            } else {
                throw new IOException("Cannot create file.");
            }
        }

        return fileCreated;
    }

    public void load() throws IOException
    {
        properties.load();
    }

    public String get(String property)
    {
        return properties.getProperty(property);
    }

    public Properties getProperties()
    {
        return properties;
    }
}
