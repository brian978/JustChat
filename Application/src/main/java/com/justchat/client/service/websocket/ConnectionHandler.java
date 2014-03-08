package com.justchat.client.service.websocket;

import com.justchat.event.EventsManager;
import com.justchat.service.AuthenticationInterface;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionHandler implements Runnable
{
    AuthenticationInterface authenticationService;
    EventsManager eventsManager;

    public ConnectionHandler(EventsManager eventsManager, AuthenticationInterface authenticationService)
    {
        this.authenticationService = authenticationService;
        this.eventsManager = eventsManager;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        if(this.authenticationService != null) {
            try {
                this.authenticationService.getConnection().connect();
            } catch (IOException | DeploymentException e) {
                HashMap<String, Object> parameters = new HashMap<>();
                parameters.put("status", "failed");
                eventsManager.trigger("connectionStatus", this, parameters);
            }
        }
    }
}
