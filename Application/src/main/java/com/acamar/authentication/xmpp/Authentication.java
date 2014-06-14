package com.acamar.authentication.xmpp;

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
public class Authentication extends AbstractAuthentication
{
    protected Connection connection = null;
    protected boolean abortAuthentication = false;

    public Authentication()
    {
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
        return "XMPP";
    }

    /**
     * Creates a new connection object or returns an existing one
     *
     * @return Connection
     */
    public Connection getConnection()
    {
        if (connection == null) {
            connection = new Connection();
        }

        return connection;
    }

    /**
     * The method provides an synchronous way for authenticating the user (hence will freeze the UI)
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    @Override
    public synchronized void authenticate(String identity, char[] password)
    {
        AuthenticationEvent.StatusCode statusCode = AuthenticationEvent.StatusCode.SUCCESS;

        if (!connection.isConnected() && !abortAuthentication) {
            connection.connect();
        }

        if (abortAuthentication) {
            statusCode = AuthenticationEvent.StatusCode.ABORTED;
        } else if (identity.length() > 0 && password.length > 0) {
            try {
                doLogin(identity, password);
                connection.saveConfig();
            } catch (XMPPException e) {
                statusCode = AuthenticationEvent.StatusCode.FAILED;
                connection.disconnect();
                e.printStackTrace();
            }
        } else {
            statusCode = AuthenticationEvent.StatusCode.INVALID_DATA;
        }

        // Resetting the abort flag so we can retry
        abortAuthentication = false;

        Arrays.fill(password, '0');

        fireAuthenticationEvent(new AuthenticationEvent(new User(identity, "Me"), statusCode));
    }

    /**
     * Will use the existing connection to login
     *
     * @param identity For facebook this would be the username
     * @param password The password for the account
     * @throws XMPPException
     */
    protected synchronized void doLogin(String identity, char[] password) throws XMPPException
    {
        connection.login(identity, new String(password));
    }

    /**
     * The method can be called when the cancel button on the interface is pressed
     * If the connection has not yet been made (in case it's async) then it will set a flag that will deny the
     * connection to be made
     *
     * @return boolean
     */
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
