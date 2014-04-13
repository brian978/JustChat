package com.acamar.net;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
abstract public class AsyncConnection extends Connection implements AsyncConnectionInterface
{
    public AsyncConnection(String protocol, String host, int port)
    {
        super(protocol, host, port);
    }

    abstract public void asyncConnect();
}
