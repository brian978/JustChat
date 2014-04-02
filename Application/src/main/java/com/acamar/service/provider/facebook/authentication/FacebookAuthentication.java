package com.acamar.service.provider.facebook.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class FacebookAuthentication extends AsyncAbstractAuthentication
{
    String server = "chat.facebook.com";
    int port = 5222;
    Socket connection = null;
    PrintWriter connOut = null;
    BufferedReader connIn = null;
    AuthRequest request = new AuthRequest();
    AuthResponse response = new AuthResponse();

    public FacebookAuthentication()
    {
        try {
            connection = new Socket(server, port);
            connOut = new PrintWriter(connection.getOutputStream());
            connIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void asyncAuthenticate(String identity, char[] password)
    {
        int statusCode = 0;
        boolean authenticated = false;

        // Quick return
        if (connection != null) {
            try {
                initiateAuth();


                LoginRequest loginRequest = new LoginRequest(identity, password);
                authenticated = true;
                statusCode = 200;
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
            }
        }

        fireAuthenticationEvent(new AuthenticationEvent(authenticated, statusCode));
    }

    private void initiateAuth()
    {
    }
}
