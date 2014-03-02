package com.justchat.client.websocket;

import com.justchat.client.websocket.listeners.NewMessageListener;

import javax.websocket.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
@ClientEndpoint
public class Endpoint {

    NewMessageListener messageListener = null;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Opened connection to server");
    }

    @OnError
    public void onError(Session session, Throwable t)
    {
        t.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received msg: " + message);
        if(messageListener != null) {
            messageListener.onNewMessage(message);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Closed connection to server because " + closeReason.getReasonPhrase());
    }

    public void setMessageListener(NewMessageListener listener)
    {
        messageListener = listener;
    }
}
