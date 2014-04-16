package com.acamar.net;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface AsyncConnectionInterface extends ConnectionInterface
{
    public void connectAsync() throws ConnectionException;
}
