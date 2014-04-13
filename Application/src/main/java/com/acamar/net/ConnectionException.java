package com.acamar.net;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionException extends Exception
{
    public ConnectionException()
    {
    }

    public ConnectionException(String message)
    {
        super(message);
    }

    public ConnectionException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public ConnectionException(Throwable cause)
    {
        super(cause);
    }

    public ConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
