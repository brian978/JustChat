package com.acamar.authentication;

import com.acamar.event.EventInterface;
import com.acamar.event.EventManager;
import com.acamar.event.FireEventCallback;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractAuthentication implements AuthenticationInterface, AsyncAuthenticationInterface
{
    /**
     * The method provides an asynchronous way for authenticating the user
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    @Override
    public void authenticateAsync(final String identity, final char[] password)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                authenticate(identity, password);
            }
        });

        thread.start();
    }

    /**
     * Adds an event listener to the global event manager
     *
     * @param listener An object that will be called after the authentication is done
     * @return AuthenticationInterface
     */
    public AuthenticationInterface addAuthenticationListener(AuthenticationListener listener)
    {
        EventManager.add(AuthenticationListener.class, listener);

        return this;
    }

    /**
     * Removes a listener from the global event manager
     *
     * @param listener The listener object to be removed
     * @return AuthenticationInterface
     */
    public AuthenticationInterface removeAuthenticationListener(AuthenticationListener listener)
    {
        EventManager.remove(AuthenticationListener.class, listener);

        return this;
    }

    /**
     * The method is called from within the authentication object when an authentication event occurs (like login)
     *
     * @param e An event object to be sent to all attached listeners of the AuthenticationListener type
     */
    protected void fireAuthenticationEvent(AuthenticationEvent e)
    {
        EventManager.fireEvent(AuthenticationListener.class, e, new FireEventCallback()
        {
            @Override
            public void fireEvent(Object listener, EventInterface e)
            {
                ((AuthenticationListener) listener).authenticationPerformed((AuthenticationEvent) e);
            }
        });
    }
}
