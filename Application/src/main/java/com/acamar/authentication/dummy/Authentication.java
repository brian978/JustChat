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
    @Override
    public void authenticate(String identity, char[] password)
    {
        fireAuthenticationEvent(new AuthenticationEvent(null, AuthenticationEvent.StatusCode.SUCCESS));
    }
}
