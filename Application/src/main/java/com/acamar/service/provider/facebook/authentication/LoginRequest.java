package com.acamar.service.provider.facebook.authentication;

import com.acamar.util.Base64;
import com.acamar.util.ResourceResolver;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class LoginRequest extends AuthRequest
{
    File authXml = null;

    public LoginRequest(String identity, char[] password) throws ParserConfigurationException, IOException, SAXException, NullPointerException
    {
        authXml = new File(ResourceResolver.getResource(LoginRequest.class, "/xml/auth_plain.xml").getPath());
        document = documentBuilderFactory.newDocumentBuilder().parse(authXml);

        if (document != null) {
            Node authNode = document.getFirstChild();
            authNode.setTextContent(Base64.encode("\\x00" + identity + "\\x00" + new String(password)));

            System.out.println("XML: " + authNode.getTextContent());
        }
    }
}
