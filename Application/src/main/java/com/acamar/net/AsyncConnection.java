package com.acamar.net;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
abstract public class AsyncConnection extends Connection implements AsyncConnectionInterface
{
    protected AsyncConnection()
    {
    }

    public AsyncConnection(String protocol, String host, int port)
    {
        super(protocol, host, port);
    }

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
}
