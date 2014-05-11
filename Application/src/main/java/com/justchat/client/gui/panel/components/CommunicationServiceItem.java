package com.justchat.client.gui.panel.components;

import com.acamar.authentication.xmpp.Authentication;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-05-09
 */
public class CommunicationServiceItem<T extends Authentication>
{
    private Class<T> authenticationClass;
    private String name;
    private T instance = null;


    public CommunicationServiceItem(Class<T> authenticationClass, String name)
    {
        this.authenticationClass = authenticationClass;
        this.name = name;
    }

    public T getInstance() throws IllegalAccessException, InstantiationException
    {
        if (instance == null) {
            instance = authenticationClass.newInstance();
        }

        return instance;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
