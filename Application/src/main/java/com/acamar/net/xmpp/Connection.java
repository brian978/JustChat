package com.acamar.net.xmpp;

import com.acamar.net.ConnectionEvent;
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
    protected String resource = "";

    // Connection defaults
    final public String defaultHost = "127.0.0.1";
    final public String defaultPort = "5222";
    final public String defaultResource = "Smack";

    public Connection()
    {
        host = getOption("host", defaultHost);
        port = Integer.parseInt(getOption("port", defaultPort));
        resource = getOption("resource", defaultResource);
    }

    public void setup(String host, int port, String resource)
    {
        super.setup(host, port);

        this.resource = resource;

        config.set("resource", resource);
    }

    protected void createEndpoint()
    {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        endpoint = new XMPPConnection(connectionConfiguration);
    }

    @Override
    protected String getConfigFilename()
    {
        return "xmpp.properties";
    }

    public String getResource()
    {
        return resource;
    }

    public XMPPConnection getEndpoint()
    {
        return endpoint;
    }

    public void login(String identity, String password) throws XMPPException
    {
        login(identity, password, resource);
    }

    public void login(String identity, String password, String resource) throws XMPPException
    {
        endpoint.login(identity, password, resource);
    }

    @Override
    public void connect()
    {
        if (endpoint == null) {
            createEndpoint();
        }

        try {
            endpoint.connect();
            fireConnectionEvent("", ConnectionEvent.CONNECTION_OPENED);
            connected = endpoint.isConnected();
        } catch (XMPPException e) {
            fireConnectionEvent(e.getMessage(), ConnectionEvent.ERROR_OCCURED);
        }
    }

    @Override
    public void disconnect()
    {
        // Sending an offline presence to let everyone know we disconnected
        if(isConnected()) {
            Presence offline = new Presence(Presence.Type.unavailable);
            endpoint.sendPacket(offline);
            endpoint.disconnect();
        }

        super.disconnect();

        // To force the creation of a new endpoint on connect
        endpoint = null;
    }
}
