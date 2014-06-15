package com.acamar.net.xmpp;

import com.acamar.net.AbstractConnection;
import com.acamar.net.ConnectionEvent;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Connection extends AbstractConnection
{
    protected XMPPConnection endpoint = null;
    protected String resource = "";

    public Connection()
    {
        host = getOption("host", "127.0.0.1");
        port = Integer.parseInt(getOption("port", "5222"));
        resource = getOption("resource", "Smack");
    }

    /**
     * The method is used to create the properties object when the connection object is created
     *
     * @return String
     */
    @Override
    protected String getConfigFilename()
    {
        return "xmpp.properties";
    }

    /**
     * The method is called after the account data is submitted
     *
     * @param host     The host we will use to connect to
     * @param port     The port to use
     * @param resource The resource is something like a path
     */
    public void setup(String host, int port, String resource)
    {
        super.setup(host, port);

        this.resource = resource;

        config.set("resource", resource);
    }

    /**
     * Returns the resource that will be used for the login
     *
     * @return String
     */
    public String getResource()
    {
        return resource;
    }

    /**
     * The client endpoint must be created before we can connect so this will be called prior to that
     */
    protected void createEndpoint()
    {
        ConnectionConfiguration connectionConfiguration = new ConnectionConfiguration(host, port);
        endpoint = new XMPPTCPConnection(connectionConfiguration);
    }

    /**
     * We need to be able to access the endpoint object because we might
     * need different objects from it (like the roster)
     *
     * @return XMPPConnection
     */
    public XMPPConnection getEndpoint()
    {
        return endpoint;
    }

    /**
     * This method will be, most likely, called by the authentication class
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     * @throws XMPPException
     */
    public void login(String identity, String password) throws XMPPException, IOException, SmackException
    {
        endpoint.login(identity, password, resource);
    }

    /**
     * Provides a synchronous method of connecting to a server
     */
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
        } catch (XMPPException | SmackException | IOException e) {
            fireConnectionEvent(e.getMessage(), ConnectionEvent.ERROR_OCCURED);
        }
    }

    /**
     * Disconnects from the server and saves the configuration
     */
    @Override
    public void disconnect()
    {
        // Sending an offline presence to let everyone know that the user disconnected
        if (isConnected()) {
            Presence offline = new Presence(Presence.Type.unavailable);

            try {
                endpoint.sendPacket(offline);
                endpoint.disconnect();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }

        super.disconnect();

        // To force the creation of a new endpoint on connect
        endpoint = null;
    }
}
