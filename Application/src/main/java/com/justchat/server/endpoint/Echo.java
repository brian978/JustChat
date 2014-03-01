package com.justchat.server.endpoint;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */

@ServerEndpoint("/echo")
public class Echo
{
    @OnMessage
    public void onMessage(Session session, String message)
    {
        session.getAsyncRemote().sendText(message);
    }
}
