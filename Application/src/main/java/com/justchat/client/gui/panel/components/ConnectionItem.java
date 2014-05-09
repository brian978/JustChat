package com.justchat.client.gui.panel.components;

import com.acamar.net.Connection;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-05-09
 */
public class ConnectionItem<T extends Connection>
{
    private Class<T> connectionClass;
    private String name;
    private T instance = null;


    public ConnectionItem(Class<T> connectionClass, String name)
    {
        this.connectionClass = connectionClass;
        this.name = name;
    }

    public T getInstance() throws IllegalAccessException, InstantiationException
    {
        if (instance == null) {
            instance = connectionClass.newInstance();
        }

        return instance;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
