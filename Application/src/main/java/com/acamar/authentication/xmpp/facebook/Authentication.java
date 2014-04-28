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
    public Authentication(Connection connection)
    {
        super(connection);
    }

    protected void doLogin(String identity, char[] password) throws XMPPException
    {
        connection.login(identity, new String(password));
    }
}
