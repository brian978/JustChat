package com.justchat.client.frame;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationAwareInterface;
import com.acamar.gui.swing.frame.AbstractFrame;
import com.acamar.net.ConnectionAwareInterface;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
abstract public class AbstractMainFrame extends AbstractFrame
        implements AuthenticationAwareInterface, ConnectionAwareInterface
{
    protected Properties settings;
    protected Authentication xmppAuthentication;
    protected Connection xmppConnection;

    public AbstractMainFrame(String title, Properties settings)
    {
        super(title);

        this.settings = settings;
    }

    @Override
    public AbstractMainFrame setAuthentication(AbstractAuthentication authentication)
    {
        this.xmppAuthentication = (Authentication) authentication;

        return this;
    }

    @Override
    public AbstractMainFrame setConnection(com.acamar.net.Connection connection)
    {
        this.xmppConnection = (Connection) connection;

        return this;
    }
}
