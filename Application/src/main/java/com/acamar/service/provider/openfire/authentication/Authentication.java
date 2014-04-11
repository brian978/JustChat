package com.acamar.service.provider.openfire.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;

import java.io.IOException;
import java.net.Socket;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Authentication extends AsyncAbstractAuthentication
{
    @Override
    protected void asyncAuthenticate(String identity, char[] password)
    {
        try {
            Socket connection = new Socket("127.0.0.1", 5222);
            System.out.println(connection.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }


        fireAuthenticationEvent(new AuthenticationEvent(true, 200));
    }
}
