package com.acamar.authentication;

import com.acamar.event.EventInterface;
import com.acamar.event.EventManager;
import com.acamar.event.FireEventCallback;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractAuthentication implements AuthenticationInterface
{
    /**
     * -----------------
     * Status codes
     * -----------------
     */
    public static final int SUCCESS = 200;
    public static final int FAILED = 300;
    public static final int INVALID_DATA = 310;
    public static final int ABORTED = 400;

    public AuthenticationInterface addAuthenticationListener(AuthenticationListener listener)
    {
        EventManager.add(AuthenticationListener.class, listener);

        return this;
    }

    public AuthenticationInterface removeAuthenticationListener(AuthenticationListener listener)
    {
        EventManager.remove(AuthenticationListener.class, listener);

        return this;
    }

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
