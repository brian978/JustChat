package com.acamar.authentication.dummy;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Authentication extends AbstractAuthentication
{
    /**
     * The method provides an synchronous way for authenticating the user (hence will freeze the UI)
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    @Override
    public void authenticate(String identity, char[] password)
    {
        fireAuthenticationEvent(new AuthenticationEvent(null, AuthenticationEvent.StatusCode.SUCCESS));
    }
}
