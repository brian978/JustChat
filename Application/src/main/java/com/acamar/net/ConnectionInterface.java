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
    public void connect() throws ConnectionException;

    public void disconnect() throws ConnectionException;

    public Connection addConnectionStatusListener(ConnectionStatusListener listener);
}
