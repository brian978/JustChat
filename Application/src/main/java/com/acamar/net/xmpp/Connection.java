package com.acamar.net.xmpp;

import com.acamar.net.AsyncConnection;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Connection extends AsyncConnection
{
    protected XMPPConnection endpoint = null;

    public Connection()
    {
        // We need the filename so we can get the options
        configFilename = "xmpp.properties";

        //        setup(null, getOption("host", "127.0.0.1"), Integer.parseInt(getOption("port", "5222")));
    }

    protected void initializeEndpoint()
    {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        endpoint = new XMPPConnection(connectionConfiguration);
    }

    @Override
    public void connect()
    {
        // Lazy initialization
        if (endpoint == null) {
            initializeEndpoint();
        }

        try {
            endpoint.connect();
            fireConnectionEvent("", ConnectionEvent.CONNECTION_OPENED);
        } catch (XMPPException e) {
            fireConnectionEvent(e.getMessage(), ConnectionEvent.ERROR_OCCURED);
        }
    }

    @Override
    public void disconnect() throws ConnectionException
    {
        super.disconnect();
        endpoint.disconnect();
    }

    public XMPPConnection getEndpoint()
    {
        return endpoint;
    }
}
