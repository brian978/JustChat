package com.acamar.service.provider.facebook.authentication;

import com.acamar.util.Base64;
import com.acamar.util.ResourceResolver;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class LoginRequest extends AuthRequest
{
    public LoginRequest(String identity, char[] password) throws ParserConfigurationException, IOException, SAXException, NullPointerException, TransformerException
    {
        loadXml("auth_plain").createDocument();

        // Updating the document
        if (document != null) {
            Node authNode = document.getFirstChild();
            authNode.setTextContent(Base64.encode("\\x00" + identity + "\\x00" + new String(password)));

            System.out.println("XML: " + payload);
        }
    }
}
