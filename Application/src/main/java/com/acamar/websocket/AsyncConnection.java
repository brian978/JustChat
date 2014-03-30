package com.acamar.websocket;

import com.acamar.event.EventManager;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class AsyncConnection extends Connection
{
    public AsyncConnection()
    {
        super();
    }

    public AsyncConnection(String host, int port)
    {
        super(host, port);
    }

    public AsyncConnection(String protocol, String host, int port)
    {
        super(protocol, host, port);
    }

    private void asyncConnect() throws IOException, DeploymentException
    {
        session = ContainerProvider.getWebSocketContainer().connectToServer(
                this,
                ClientEndpointConfig.Builder.create().build(),
                URI.create(protocol + "://" + host + ":" + port)
        );
    }

    @Override
    public void connect()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    asyncConnect();
                } catch (IOException | DeploymentException e) {
                    fireConnectionEvent(e.getMessage(), session, ConnectionEvent.ERROR_OCCURED);
                }
            }
        });

        thread.start();
    }
}
