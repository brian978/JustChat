package com.acamar.service.authentication;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface AuthenticationInterface
{
    public void authenticate(String identity, char[] password);

    public AuthenticationInterface addAuthenticationListener(AuthenticationListener listener);
}
