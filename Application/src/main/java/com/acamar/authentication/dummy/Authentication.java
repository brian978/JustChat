package com.acamar.authentication.dummy;

import com.acamar.authentication.AbstractAsyncAuthentication;
import com.acamar.authentication.AuthenticationEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Authentication extends AbstractAsyncAuthentication
{
    @Override
    public void authenticate(String identity, char[] password)
    {
        fireAuthenticationEvent(new AuthenticationEvent(null, true, AuthenticationEvent.SUCCESS));
    }
}
