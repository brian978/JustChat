package com.acamar.authentication;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
public interface AuthenticationAwareInterface<T>
{
    public T setAuthentication(AbstractAuthentication authentication);
}
