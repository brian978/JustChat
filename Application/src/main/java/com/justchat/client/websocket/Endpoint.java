package com.justchat.client.websocket;

import com.justchat.model.user.identity.User;
import com.justchat.client.websocket.listeners.NewMessageListener;
import com.justchat.event.EventsManager;

import javax.websocket.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
@ClientEndpoint
public class Endpoint extends javax.websocket.Endpoint
{
    EventsManager eventsManager = null;
    ArrayList<NewMessageListener> messageListeners = new ArrayList<>();

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig)
    {
        System.out.println("Opened connection to server");
        if(eventsManager != null) {
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("status", "success");
            eventsManager.trigger("connectionStatus", this, parameters);
        }
    }

    @OnError
    public void onError(Session session, Throwable t)
    {
        t.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message)
    {
        System.out.println("Received msg: " + message);
        if (!messageListeners.isEmpty()) {
            for (NewMessageListener listener : messageListeners) {
                listener.onNewMessage(new User("some user", "asdsa"), message);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("Closed connection to server because " + closeReason.getReasonPhrase());
    }

    public void addMessageListener(NewMessageListener listener)
    {
        messageListeners.add(listener);
    }

    public void setEventsManager(EventsManager eventsManager)
    {
        this.eventsManager = eventsManager;
    }
}
