package com.acamar.service.provider.facebook.authentication;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class AuthResponse extends AuthXml
{
    public AuthResponse()
    {
    }

    public AuthResponse(String response)
    {
        if (response.length() > 0) {
            setResponse(response);
        }
    }

    public void setResponse(String response)
    {
        try {
            createDocument(response);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String toString()
    {
        try {
            return converToString();
        } catch (TransformerException e) {
            return "";
        }
    }
}
