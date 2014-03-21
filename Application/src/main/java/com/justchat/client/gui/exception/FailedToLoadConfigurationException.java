package com.justchat.client.gui.exception;

import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class FailedToLoadConfigurationException extends IOException
{
    public FailedToLoadConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
