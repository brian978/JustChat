package com.acamar.authentication;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public interface AsyncAuthenticationInterface extends AuthenticationInterface
{
    /**
     * The method provides an asynchronous way for authenticating the user
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    public void authenticateAsync(final String identity, final char[] password);
}
