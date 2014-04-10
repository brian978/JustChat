package com.acamar.service.provider.facebook.authentication;

import com.acamar.service.authentication.AsyncAbstractAuthentication;
import com.acamar.service.authentication.AuthenticationEvent;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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
    BufferedWriter connectionWriter = null;
    BufferedReader connectionReader = null;
    AuthRequest request = new AuthRequest();
    AuthResponse response = new AuthResponse();

    public FacebookAuthentication()
    {
        try {
            connection = new Socket(server, port);
            connectionWriter = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            connectionReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
        if (connection != null && connection.isConnected()) {
            // Populating the requests we will do

            try {
                initiateAuth();
                LoginRequest loginRequest = new LoginRequest(identity, password);

                // Successful authentication
                authenticated = true;
                statusCode = 200;
            } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
                e.printStackTrace();
            }

            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to establish a connection");
        }

        fireAuthenticationEvent(new AuthenticationEvent(authenticated, statusCode));
    }

    private void initiateAuth() throws TransformerException, IOException, ParserConfigurationException, SAXException
    {
        response = request.loadXml("initiate").send(connectionWriter, connectionReader);

        System.out.println("From server: " + response.toString());
    }
}
