package com.acamar.authentication;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public interface AuthenticationListener
{
    /**
     * The method is called by the event manager when the authentication object triggers an event
     *
     * @param e Authentication event object that was created by the authentication object
     */
    public void authenticationPerformed(AuthenticationEvent e);
}
