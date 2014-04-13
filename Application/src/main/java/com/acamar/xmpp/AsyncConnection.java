package com.acamar.xmpp;

import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class AsyncConnection extends Connection
{
    public AsyncConnection()
    {
        super();
    }

    @Override
    public void connect()
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try {
                    asyncConnect();
                } catch (ConnectionException e) {
                    fireConnectionEvent(e.getCause().getMessage(), ConnectionEvent.ERROR_OCCURED);
                }
            }
        });

        thread.start();
    }

    private void asyncConnect() throws ConnectionException
    {
        super.connect();
    }
}
