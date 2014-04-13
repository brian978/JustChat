package com.acamar.service.authentication;

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
    @Override
    public AuthenticationInterface addAuthenticationListener(AuthenticationListener listener)
    {
        EventManager.add(AuthenticationListener.class, listener);

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
