package com.acamar.websocket;

import com.acamar.event.EventInterface;
import com.acamar.event.EventManager;
import com.acamar.event.FireEventCallback;
import com.acamar.util.Properties;

import javax.websocket.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Connection extends Endpoint
{
    protected Session session = null;
    protected static Config config = Connection.getConfig();

    protected String protocol = "wss";
    protected String host = "";
    protected int port = 0;

    public Connection()
    {
        this(Connection.config.get("host"), Integer.parseInt(Connection.config.get("port")));
    }

    public Connection(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    public Connection(String protocol, String host, int port)
    {
        this(host, port);
        this.protocol = protocol;
    }

    private static Config getConfig()
    {
        if (Connection.config == null) {
            try {
                Connection.config = new Config();
            } catch (IOException e) {
                // We will handle this using an event
                e.printStackTrace();
            }
        }

        return Connection.config;
    }

    public void connect() throws IOException, DeploymentException
    {
        session = ContainerProvider.getWebSocketContainer().connectToServer(
                this,
                ClientEndpointConfig.Builder.create().build(),
                URI.create(protocol + "://" + host + ":" + port)
        );
    }

    public void disconnect() throws IOException
    {
        if (session != null) {
            session.close();
        }
    }

    public void send(String message)
    {
        if (session != null && session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    public Connection addConnectionStatusListener(ConnectionStatusListener listener)
    {
        EventManager.add(ConnectionStatusListener.class, listener);

        return this;
    }

    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig)
    {
        System.out.println("Opened connection to server");

        fireConnectionEvent("Connection OK", session, ConnectionEvent.CONNECTION_OPENED);
    }

    @OnError
    public void onError(Session session, Throwable t)
    {
        t.printStackTrace();

        fireConnectionEvent(t.getMessage(), session, ConnectionEvent.ERROR_OCCURED);
    }

    @OnMessage
    public void onMessage(String message)
    {
        System.out.println("Received message " + message);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        System.out.println("Closed connection to server because " + closeReason.getReasonPhrase());

        fireConnectionEvent(closeReason.getReasonPhrase(), session, ConnectionEvent.CONNECTION_CLOSED);

        try {
            Connection.config.save();
        } catch (IOException e) {
            // We will handle this using an event
            e.printStackTrace();
        }
    }

    protected void fireConnectionEvent(String message, Session session, int statusCode)
    {
        EventManager.fireEvent(
                ConnectionStatusListener.class,
                new ConnectionEvent(message, session, statusCode),
                new StatusChangedCallback()
        );
    }

    /**
     * The callback is used when the status of the connection changes to call the appropriate method
     */
    protected class StatusChangedCallback implements FireEventCallback
    {
        @Override
        public void fireEvent(Object listener, EventInterface e)
        {
            ((ConnectionStatusListener) listener).statusChanged((ConnectionEvent) e);
        }
    }

    /**
     * Configuration class for the connection
     */
    public static class Config
    {
        File config = new File("socket.properties");
        Properties properties = new Properties(config);

        public Config() throws IOException
        {
            this(true);
        }

        public Config(boolean autoload) throws IOException
        {
            if (!createFile() && autoload) {
                load();
            }
        }

        private boolean createFile() throws IOException
        {
            boolean fileCreated = false;

            if (!config.exists()) {
                if (config.createNewFile()) {
                    properties.setProperty("host", "brian.hopto.org");
                    properties.setProperty("port", "7896");
                    properties.store();
                    fileCreated = true;
                } else {
                    throw new IOException("Cannot create file.");
                }
            }

            return fileCreated;
        }

        public void load() throws IOException
        {
            properties.load();
        }

        public void save() throws IOException
        {
            properties.store();
        }

        public void set(String name, String property)
        {
            properties.setProperty(name, property);
        }

        public String get(String property)
        {
            return properties.getProperty(property);
        }
    }
}
