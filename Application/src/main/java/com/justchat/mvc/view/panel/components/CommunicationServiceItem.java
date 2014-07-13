package com.justchat.mvc.view.panel.components;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventManager;

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
    private EventManager eventManager;

    private T instance = null;

    /**
     *
     * @param authenticationClass The class the will be used to create the authentication object
     * @param name The name that will be displayed in the combo box for this item
     * @param eventManager An event manager object that will be used by the authentication and connection objects
     */
    public CommunicationServiceItem(Class<T> authenticationClass, String name, EventManager eventManager)
    {
        this.authenticationClass = authenticationClass;
        this.name = name;
        this.eventManager = eventManager;
    }

    /**
     * The method returns the instance of an object that was created using the authentication class
     *
     * @return T
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public T getInstance() throws IllegalAccessException, InstantiationException
    {
        if (instance == null) {
            instance = authenticationClass.newInstance();
            instance.setEventManager(eventManager);
            instance.getConnection().setEventManager(eventManager);
        }

        return instance;
    }

    /**
     * Returns a string representation of the object
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return name;
    }
}
