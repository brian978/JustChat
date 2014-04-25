package com.acamar.net;

import com.acamar.event.EventInterface;
import com.acamar.event.EventManager;
import com.acamar.event.FireEventCallback;
import com.acamar.util.Properties;

import java.io.File;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
abstract public class Connection implements ConnectionInterface, ConnectionAsyncInterface
{
    protected Properties config = new Properties(getConfigFilename());
    protected String protocol = "";
    protected String host = "";
    protected int port = 0;

    protected Connection()
    {

    }

    public Connection(String protocol, String host, int port)
    {
        setup(protocol, host, port);
    }

    abstract protected String getConfigFilename();

    @Override
    public void connectAsync()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    connect();
                } catch (ConnectionException e) {
                    fireConnectionEvent(e.getCause().getMessage(), ConnectionEvent.ERROR_OCCURED);
                }
            }
        });

        thread.start();
    }

    public void setup(String protocol, String host, int port)
    {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public void disconnect() throws ConnectionException
    {
        try {
            config.store();
        } catch (IOException e) {
            // We will handle this using an event
            e.printStackTrace();
        }
    }

    public Connection addConnectionStatusListener(ConnectionStatusListener listener)
    {
        EventManager.add(ConnectionStatusListener.class, listener);

        return this;
    }

    protected void fireConnectionEvent(String message, int statusCode)
    {
        EventManager.fireEvent(
                ConnectionStatusListener.class,
                new ConnectionEvent(message, statusCode),
                new FireEventCallback()
                {
                    @Override
                    public void fireEvent(Object listener, EventInterface e)
                    {
                        ((ConnectionStatusListener) listener).statusChanged((ConnectionEvent) e);
                    }
                }
        );
    }

    protected String getOption(String name)
    {
        return getOption(name, null);
    }

    protected String getOption(String name, String defaultValue)
    {
        if (!config.isLoaded()) {
            config.checkAndLoad();
        }

        return config.get(name, defaultValue);
    }
}
