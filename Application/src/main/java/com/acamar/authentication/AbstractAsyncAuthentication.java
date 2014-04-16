package com.acamar.authentication;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public abstract class AbstractAsyncAuthentication extends AbstractAuthentication implements AsyncAuthenticationInterface
{
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
}
