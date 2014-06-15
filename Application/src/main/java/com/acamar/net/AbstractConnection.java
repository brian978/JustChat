package com.acamar.net;

import com.acamar.event.EventManager;
import com.acamar.util.Properties;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
abstract public class AbstractConnection implements ConnectionInterface, ConnectionAsyncInterface
{
    protected Properties config = new Properties(getConfigFilename());
    protected String host = "";
    protected int port = 0;
    protected boolean connected = false;

    public AbstractConnection()
    {
        host = getOption("host");

        try {
            port = Integer.parseInt(getOption("port"));
        } catch (NumberFormatException e) {
            port = 0;
        }
    }

    /**
     * The method is used to create the properties object when the connection object is created
     *
     * @return String
     */
    abstract protected String getConfigFilename();

    /**
     *
     * @return String
     */
    public String getHost()
    {
        return host;
    }

    /**
     *
     * @return int
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Configures the connection object
     * It also sets the configuration parameters in the configuration object so they can be saved
     *
     * @param host Host to connect to
     * @param port Port to connect to
     */
    public void setup(String host, int port)
    {
        this.host = host;
        this.port = port;

        // Storing the data as well
        config.set("host", host);
        config.set("port", String.valueOf(port));
    }

    /**
     *
     * @return boolean
     */
    public boolean isConnected()
    {
        return connected;
    }

    /**
     * The method provides a asynchronous method of connecting to a server
     *
     */
    @Override
    public void connectAsync()
    {
        Thread thread = new Thread(
                new Runnable()
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
                }
        );

        thread.start();
    }

    public void disconnect()
    {
        connected = false;

        saveConfig();
    }

    public AbstractConnection addConnectionStatusListener(ConnectionStatusListener listener)
    {
        EventManager.add(ConnectionStatusListener.class, listener);

        return this;
    }

    public AbstractConnection removeConnectionStatusListener(ConnectionStatusListener listener)
    {
        EventManager.remove(ConnectionStatusListener.class, listener);

        return this;
    }

    protected void fireConnectionEvent(String message, int statusCode)
    {
        try {
            EventManager.fireEvent(
                    ConnectionStatusListener.class,
                    new ConnectionEvent(message, statusCode),
                    ConnectionStatusListener.class.getMethod("statusChanged", ConnectionEvent.class)
            );
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
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

    /**
     * The configuration is save usually after a successful login so we have the data at next login
     */
    public void saveConfig()
    {
        try {
            config.store();
        } catch (IOException e) {
            // We will handle this using an event
            e.printStackTrace();
        }
    }
}
