package com.acamar.authentication;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface AsyncAuthenticationInterface extends AuthenticationInterface
{
    public void authenticateAsync(String identity, char[] password);
}
