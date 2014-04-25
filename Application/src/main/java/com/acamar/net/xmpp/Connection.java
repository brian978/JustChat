package com.acamar.net.xmpp;

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
public class Connection extends com.acamar.net.Connection
{
    protected XMPPConnection endpoint = null;

    // Connection defaults
    final public String defaultHost = "127.0.0.1";
    final public String defaultPort = "5222";

    public Connection()
    {
        setup(getOption("host", defaultHost), Integer.parseInt(getOption("port", defaultPort)));
    }

    @Override
    protected String getConfigFilename()
    {
        return "xmpp.properties";
    }

    protected void createEndpoint()
    {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        endpoint = new XMPPConnection(connectionConfiguration);
    }

    public XMPPConnection getEndpoint()
    {
        if(endpoint == null) {
            createEndpoint();
        }

        return endpoint;
    }

    @Override
    public void connect()
    {
        try {
            getEndpoint().connect();
            fireConnectionEvent("", ConnectionEvent.CONNECTION_OPENED);
        } catch (XMPPException e) {
            fireConnectionEvent(e.getMessage(), ConnectionEvent.ERROR_OCCURED);
        }
    }

    @Override
    public void disconnect() throws ConnectionException
    {
        // Sending an offline presence to let everyone know we disconnected
        Presence offline = new Presence(Presence.Type.unavailable);
        endpoint.sendPacket(offline);

        endpoint.disconnect();
        super.disconnect();
    }
}
