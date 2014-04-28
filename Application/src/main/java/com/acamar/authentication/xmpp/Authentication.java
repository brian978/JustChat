package com.acamar.authentication.xmpp;

import com.acamar.authentication.AbstractAsyncAuthentication;
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
        boolean success = false;
        String message = "Incorrect login";

        if (!connection.isConnected() && !abortAuthentication) {
            connection.connect();
        }

        if (abortAuthentication) {
            message = "Authentication canceled";
        } else if (identity.length() > 0 && password.length > 0) {
            try {
                doLogin(identity, password);
                success = true;
                message = "OK!";

            } catch (XMPPException e) {
                connection.disconnect();
                e.printStackTrace();
            }
        } else {
            message = "Invalid data";
        }

        Arrays.fill(password, '0');

        fireAuthenticationEvent(new AuthenticationEvent(new User(identity, "Me"), success, 0, message));

        // Resetting the abort flag so we can retry
        abortAuthentication = false;
    }

    protected synchronized void doLogin(String identity, char[] password) throws XMPPException
    {
        connection.login(identity, new String(password));
    }

    public void cancel()
    {
        abortAuthentication = true;
        connection.disconnect();
    }
}
