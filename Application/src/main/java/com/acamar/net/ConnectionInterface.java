package com.acamar.net;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface ConnectionInterface
{
    /**
     * Provides a synchronous method of connecting to a server
     *
     */
    public void connect();

    /**
     * Disconnects the user from the server
     *
     */
    public void disconnect();
}
