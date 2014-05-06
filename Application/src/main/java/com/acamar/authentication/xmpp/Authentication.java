package com.acamar.authentication.xmpp;

import com.acamar.authentication.AbstractAsyncAuthentication;
import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.net.xmpp.Connection;
import com.acamar.users.User;
import org.jivesoftware.smack.XMPPException;

import java.util.Arrays;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Authentication extends AbstractAsyncAuthentication
{
    protected Connection connection = null;
    protected boolean abortAuthentication = false;

    public Authentication(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public synchronized void authenticate(String identity, char[] password)
    {
        int statusCode = AbstractAuthentication.SUCCESS;

        if (!connection.isConnected() && !abortAuthentication) {
            connection.connect();
        }

        if (abortAuthentication) {
            statusCode = AbstractAuthentication.ABORTED;
        } else if (identity.length() > 0 && password.length > 0) {
            try {
                doLogin(identity, password);
            } catch (XMPPException e) {
                statusCode = AbstractAuthentication.FAILED;
                connection.disconnect();
                e.printStackTrace();
            }
        } else {
            statusCode = AbstractAuthentication.INVALID_DATA;
        }

        // Resetting the abort flag so we can retry
        abortAuthentication = false;

        Arrays.fill(password, '0');

        fireAuthenticationEvent(new AuthenticationEvent(new User(identity, "Me"), statusCode));
    }

    protected synchronized void doLogin(String identity, char[] password) throws XMPPException
    {
        connection.login(identity, new String(password));
    }

    public synchronized boolean cancel()
    {
        if (!connection.isConnected()) {
            abortAuthentication = true;
            connection.disconnect();
            return true;
        }

        return false;
    }
}
