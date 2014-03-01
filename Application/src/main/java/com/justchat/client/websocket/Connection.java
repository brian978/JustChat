package com.justchat.client.websocket;

import java.io.IOException;
import java.net.URI;
import javax.websocket.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */

@ClientEndpoint
public class Connection
{
    String host = "";
    int port = 0;
    Session session = null;
    WebSocketContainer client = ContainerProvider.getWebSocketContainer();

    public Connection(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    @OnMessage
    public void onMessage(String message)
    {
        //the new USD rate arrives from the web socket server side.
        System.out.println("Received msg: " + message);
    }

    public void connect() throws IOException, DeploymentException
    {
        session = client.connectToServer(Connection.class, URI.create("ws://" + host + ":" + port));
    }

    public void disconnect() throws IOException
    {
        if (session != null) {
            session.close();
        }
    }

    public void sendMessage(String message)
    {

    }
}
