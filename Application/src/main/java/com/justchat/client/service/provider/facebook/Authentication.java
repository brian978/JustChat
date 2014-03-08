package com.justchat.client.service.provider.facebook;

import com.justchat.client.identity.User;
import com.justchat.client.websocket.Connection;
import com.justchat.event.EventsManager;
import com.justchat.service.AbstractAuthentication;

import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Authentication extends AbstractAuthentication
{
    public Authentication(EventsManager eventsManager, Connection connection)
    {
        super(eventsManager, connection);
    }

    @Override
    public User authenticate(String identifier, String password)
    {
        HashMap<String, Object> parameters = new HashMap<>();

        if(identifier.length() > 0 && password.length() > 0) {
            System.out.println("Authenticating user with identifier " + identifier + " and password " + password);
            user = new User(identifier);
            parameters.put("status", "success");
            parameters.put("user", user);
        } else {
            parameters.put("status", "failed");
            parameters.put("message", "Invalid login details");
        }

        eventsManager.trigger("authenticationStatus", this, parameters);

        return user;
    }
}
