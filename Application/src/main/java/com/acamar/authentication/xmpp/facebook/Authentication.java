package com.acamar.authentication.xmpp.facebook;

import com.acamar.net.xmpp.facebook.Connection;
import org.jivesoftware.smack.XMPPException;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-18
 */
public class Authentication extends com.acamar.authentication.xmpp.Authentication
{
    /**
     * Creates a new connection object or returns an existing one
     *
     * @return Connection
     */
    @Override
    public Connection getConnection()
    {
        if (connection == null) {
            connection = new Connection();
        }

        return (Connection) connection;
    }

    /**
     * Will use the existing connection to login
     *
     * @param identity For facebook this would be the username
     * @param password The password for the account
     * @throws XMPPException
     */
    @Override
    protected void doLogin(String identity, char[] password) throws XMPPException
    {
        connection.login(identity, new String(password));
    }

    /**
     * Returns the string that identifies the authentication method
     * (for example it can be used by the JComboBox when this object is added to it)
     *
     * @return String
     */
    @Override
    public String toString()
    {
        return "XMPP (Facebook)";
    }
}
