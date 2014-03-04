package com.justchat.client.service.websocket;

import com.justchat.client.websocket.Connection;
import com.justchat.client.websocket.factory.ConnectionFactory;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConnectionHandler implements Runnable
{
    public ConnectionHandler()
    {

    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        try {
            Connection connection = ConnectionFactory.factory();
            connection.connect();
        } catch (IOException | DeploymentException e) {
        }
    }
}
