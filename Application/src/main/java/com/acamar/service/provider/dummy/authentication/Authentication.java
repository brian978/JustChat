package com.acamar.service.provider.dummy.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Authentication extends AsyncAbstractAuthentication
{
    @Override
    protected void asyncAuthenticate(String identity, char[] password)
    {
        fireAuthenticationEvent(new AuthenticationEvent(true, 200));
    }
}
