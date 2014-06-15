package com.acamar.net;

/**
 * JustChat
 *
 * The interface is used to determine if an object supports the injection of a connection object
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
public interface ConnectionAwareInterface<T>
{
    /**
     * Is used to inject a connection object into another object
     *
     * @param connection ConnectionInterface
     * @return T
     */
    public T setConnection(AbstractConnection connection);
}
