package com.acamar.net.xmpp;

import com.acamar.net.AsyncConnection;
import com.acamar.net.ConnectionEvent;
import com.acamar.net.ConnectionException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

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
        initializeEndpoint();
    }

    @Override
    protected String getConfigFilename()
    {
        return "xmpp.properties";
    }

    protected void setup()
    {
        setup(null, getOption("host", "127.0.0.1"), Integer.parseInt(getOption("port", "5222")));
    }

    protected void initializeEndpoint()
    {
        setup();

        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        endpoint = new XMPPConnection(connectionConfiguration);
    }

    @Override
    public void connect()
    {
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

        // Sending an offline presence to let everyone know we disconnected
        Presence offline = new Presence(Presence.Type.unavailable);
        endpoint.sendPacket(offline);

        // Disconnecting the socket
        endpoint.disconnect();
    }

    public XMPPConnection getEndpoint()
    {
        return endpoint;
    }
}
