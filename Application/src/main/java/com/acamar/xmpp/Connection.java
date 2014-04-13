package com.acamar.xmpp;

import com.acamar.net.ConnectionException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Connection extends com.acamar.net.Connection
{
    protected XMPPConnection endpoint = null;

    public Connection()
    {
        config = getConfig("xmpp.properties");
        setup(null, getOption("host", "127.0.0.1"), Integer.parseInt(getOption("port", "5222")));

        endpoint = new XMPPConnection(host);
    }

    @Override
    public void connect() throws ConnectionException
    {
        try {
            endpoint.connect();
        } catch (XMPPException e) {
            throw new ConnectionException(e);
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
