package com.acamar.authentication;

import com.acamar.event.EventManager;
import com.acamar.event.EventManagerAwareInterface;
import com.acamar.users.User;

import java.util.HashMap;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractAuthentication
        implements AuthenticationInterface, AsyncAuthenticationInterface, EventManagerAwareInterface
{
    protected EventManager eventManager = null;

    /**
     * The method provides an asynchronous way for authenticating the user
     *
     * @param identity String that identifies the user on the server
     * @param password Password of the account
     */
    @Override
    public void authenticateAsync(final String identity, final char[] password)
    {
        Thread thread = new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        authenticate(identity, password);
                    }
                }
        );

        thread.start();
    }

    /**
     * The method is called from within the authentication object when an authentication event occurs (like login)
     *
     * @param identity   Identity (username/email) of the user that was authenticated (or tried to be authenticated)
     * @param statusCode Status codes for the authentication process
     */
    protected void fireAuthenticationEvent(String identity, AuthenticationEvent.StatusCode statusCode)
    {
        HashMap<Object, Object> eventParams = new HashMap<>();
        eventParams.put("object", this);
        eventParams.put("identity", identity);
        eventParams.put("statusCode", statusCode);

        eventManager.trigger(new AuthenticationEvent(this, eventParams));
    }

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * Returns the event manager object that was injected or created inside this object
     *
     * @return EventManager
     */
    @Override
    public EventManager getEventManager()
    {
        return eventManager;
    }
}
