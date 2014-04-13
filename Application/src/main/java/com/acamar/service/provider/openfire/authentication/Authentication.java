package com.acamar.service.provider.openfire.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;

import java.util.Arrays;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Authentication extends AsyncAbstractAuthentication
{
    XMPPConnection connection = null;

    public Authentication(XMPPConnection connection)
    {
        this.connection = connection;
    }

    @Override
    protected void asyncAuthenticate(String identity, char[] password)
    {
        boolean success = false;
        String message = "Incorrect login";

        if(identity.length() > 0 && password.length > 0) {
            try {
                connection.login(identity, new String(password));
                success = true;
                message = "";

            } catch (XMPPException e) {
                e.printStackTrace();
            }
        } else {
            message = "Invalid data";
        }

        Arrays.fill(password, '0');

        fireAuthenticationEvent(new AuthenticationEvent(success, 0, message));
    }
}
