package com.acamar.service.provider.facebook.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class FacebookAuthentication extends AsyncAbstractAuthentication
{
    @Override
    protected void asyncAuthenticate(String identity, char[] password)
    {
        LoginRequest loginRequest = new LoginRequest(identity, password);

        fireAuthenticationEvent(new AuthenticationEvent(true, 200));
    }
}
