package com.acamar.authentication;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public interface AuthenticationInterface
{
    /**
     * The method provides an synchronous way for authenticating the user (hence will freeze the UI)
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    public void authenticate(String identity, char[] password);
}
