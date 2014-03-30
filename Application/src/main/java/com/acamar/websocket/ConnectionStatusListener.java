package com.acamar.websocket;

import com.acamar.websocket.ConnectionEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface ConnectionStatusListener
{
    public void statusChanged(ConnectionEvent e);

    public void messageReceived(ConnectionEvent e);
}
