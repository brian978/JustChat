package com.acamar.service.authentication;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public interface AuthenticationInterface
{
    public void authenticate(String identity, char[] password);

    public AuthenticationInterface addAuthenticationListener(AuthenticationListener listener);
}
