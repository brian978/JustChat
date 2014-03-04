package com.justchat.client.websocket;

import com.justchat.client.identity.User;
import com.justchat.client.websocket.listeners.ConnectionStatusListener;
import com.justchat.client.websocket.listeners.NewMessageListener;

import javax.websocket.*;
import java.util.ArrayList;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
@ClientEndpoint
public class Endpoint
{
    ArrayList<NewMessageListener> messageListeners = new ArrayList<>();
    ArrayList<ConnectionStatusListener> statusListeners = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println("Opened connection to server");
        if (!statusListeners.isEmpty()) {
            for (ConnectionStatusListener listener : statusListeners) {
                listener.onConnectionEstablished();
            }
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
                listener.onNewMessage(new User("some user"), message);
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

    public void addStatusListener(ConnectionStatusListener statusListener)
    {
        statusListeners.add(statusListener);
    }
}
