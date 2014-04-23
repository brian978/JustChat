package com.acamar.net;

import com.acamar.net.Connection;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
public interface ConnectionAwareInterface<T>
{
    public T setConnection(Connection connection);
}
