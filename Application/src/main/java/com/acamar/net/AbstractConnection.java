package com.acamar.net;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.util.Properties;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractConnection implements ConnectionInterface, ConnectionAsyncInterface, EventManagerAwareInterface
{
    protected EventManager eventManager = null;
    protected Properties config = new Properties(getConfigFilename());
    protected String host = "";
    protected int port = 0;
    protected boolean connected = false;

    public AbstractConnection()
    {
        host = getOption("host");
        port = Integer.parseInt(getOption("port", "0"));
    }

    /**
     * The method is used to create the properties object when the connection object is created
     *
     * @return String
     */
    abstract protected String getConfigFilename();

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
     * @return String
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @return int
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Checks if the connection is established
     *
     * @return boolean
     */
    public boolean isConnected()
    {
        return connected;
    }

    /**
     * The method provides a asynchronous method of connecting to a server
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
                        connect();
                    }
                }
        );

        thread.start();
    }

    /**
     * Disconnects from the server and saves the configuration
     */
    public void disconnect()
    {
        connected = false;

        saveConfig();
    }

    /**
     * Can be used to add a event listener for connection events
     *
     * @param listener Listener object that will listen for connection events
     * @return AbstractConnection
     */
    public AbstractConnection addConnectionStatusListener(ConnectionStatusListener listener)
    {
        eventManager.add(ConnectionStatusListener.class, listener);

        return this;
    }

    /**
     * Can be used to remove a event listener for connection events
     *
     * @param listener Listener object that will listen for connection events
     * @return AbstractConnection
     */
    public AbstractConnection removeConnectionStatusListener(ConnectionStatusListener listener)
    {
        eventManager.remove(ConnectionStatusListener.class, listener);

        return this;
    }

    /**
     * Builds and fires a connection event
     *
     * @param message    Message that will be contained in the event object
     * @param statusCode Status code so we can determine from the event what happened
     */
    protected void fireConnectionEvent(String message, ConnectionEvent.StatusCode statusCode)
    {
        HashMap<Object, Object> eventParams = new HashMap<>();
        eventParams.put("message", message);
        eventParams.put("statusCode", statusCode);

        try {
            eventManager.fireEvent(
                    ConnectionStatusListener.class,
                    new ConnectionEvent(this, eventParams),
                    ConnectionStatusListener.class.getMethod("statusChanged", ConnectionEvent.class)
            );
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an option from the configuration object. If the option is not found it will return NULL
     *
     * @param name Option name from config
     * @return String
     */
    protected String getOption(String name)
    {
        return getOption(name, null);
    }

    /**
     * Returns an option from the configuration object
     *
     * @param name         Option name from config
     * @param defaultValue The default value to be returned if the option is not found
     * @return String
     */
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

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager()
    {
        return eventManager;
    }
}
