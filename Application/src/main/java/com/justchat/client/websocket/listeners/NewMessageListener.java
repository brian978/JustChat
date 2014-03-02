package com.justchat.client.websocket.listeners;

import com.justchat.client.identity.User;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public interface NewMessageListener
{
    public void onNewMessage(User user, String message);
}
