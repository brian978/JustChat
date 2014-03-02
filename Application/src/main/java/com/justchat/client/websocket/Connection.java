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
public class Connection
{
    String host = "";
    int port = 0;
    Session session = null;
    WebSocketContainer client = ContainerProvider.getWebSocketContainer();
    Endpoint clientEndpoint = new Endpoint();

    public Connection(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException, DeploymentException
    {
        session = client.connectToServer(clientEndpoint, URI.create("ws://" + host + ":" + port));
    }

    public void disconnect() throws IOException
    {
        if (session != null) {
            session.close();
        }
    }

    public void sendMessage(String message)
    {
        session.getAsyncRemote().sendText(message);
    }

    public Endpoint getEndpoint()
    {
        return clientEndpoint;
    }
}
