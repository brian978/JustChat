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
abstract public class Connection implements ConnectionInterface
{
    protected Config config = null;
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

    public void setup(String protocol, String host, int port)
    {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public void disconnect() throws ConnectionException
    {
        try {
            config.save();
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

    protected Config getConfig(String filename)
    {
        if (config == null) {
            try {
                config = new Config(filename);
            } catch (IOException e) {
                // We will handle this using an event
                e.printStackTrace();
            }
        }

        return config;
    }

    protected String getOption(String name)
    {
        return getOption(name, null);
    }

    protected String getOption(String name, String defaultValue)
    {
        return config.get(name, defaultValue);
    }

    /**
     * Configuration class for the connection
     */
    protected class Config
    {
        File config = null;
        Properties properties = null;

        public Config(String filename) throws IOException
        {
            this(filename, true);
        }

        public Config(String filename, boolean autoload) throws IOException
        {
            config = new File(filename);
            properties = new Properties(config);

            if (!createFile() && autoload) {
                load();
            }
        }

        private boolean createFile() throws IOException
        {
            boolean fileCreated = false;

            if (!config.exists()) {
                if (config.createNewFile()) {
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

        public String get(String property, String defaultValue)
        {
            String value = properties.getProperty(property);
            if (value == null) {
                properties.setProperty(property, defaultValue);
                value = defaultValue;
            }

            return value;
        }
    }
}
