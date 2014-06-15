package com.acamar.authentication;

/**
 * JustChat
 *
 * The interface is used to determine if an object supports the injection of a authentication object
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-23
 */
public interface AuthenticationAwareInterface<T>
{
    /**
     * Is used to inject an authentication object into another object
     *
     * @param authentication Authentication object
     * @return T
     */
    public T setAuthentication(AbstractAuthentication authentication);

    /**
     * Returns the authentication object set in an object
     *
     * @return AbstractAuthentication
     */
    public AbstractAuthentication getAuthentication();
}
