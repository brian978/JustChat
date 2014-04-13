package com.acamar.service.authentication;

import java.util.Arrays;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AsyncAbstractAuthentication extends AbstractAuthentication
{
    protected abstract void asyncAuthenticate(String identity, char[] password);

    @Override
    public void authenticate(final String identity, final char[] password)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                asyncAuthenticate(identity, password);

                // Resetting the array for security reasons
                Arrays.fill(password, '0');
            }
        });

        thread.start();
    }
}
