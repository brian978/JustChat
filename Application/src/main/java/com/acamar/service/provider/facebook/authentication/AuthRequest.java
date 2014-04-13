package com.acamar.service.provider.facebook.authentication;

import com.acamar.util.ResourceResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.Socket;
import java.net.URL;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class AuthRequest extends AuthXml
{
    protected File xml = null;
    protected String payload = "";
    protected boolean lazyLoad = false;
    protected String xmlName = "";

    public AuthRequest()
    {
    }

    public AuthRequest(String xmlName)
    {
        lazyLoad = true;
        this.xmlName = xmlName;
    }

    public AuthRequest loadXml(String xmlName)
    {
        URL resource = ResourceResolver.getResource(AuthRequest.class, "/xml/" + xmlName + ".xml");
        xml = new File(resource.getPath());

        return this;
    }

    protected void createDocument() throws ParserConfigurationException, IOException, SAXException
    {
        document = documentBuilderFactory.newDocumentBuilder().parse(xml);
    }

    public AuthResponse send(BufferedWriter writer, BufferedReader reader) throws TransformerException, IOException, SAXException, ParserConfigurationException
    {
        if (lazyLoad) {
            lazyLoad = false;
            loadXml(xmlName);
        }

        String xmlResponse = "";

        // Sending the data
        createDocument();

        try {
            String payload = converToString();

            System.out.println("Will send: " + payload);

            writer.write(payload);
            writer.flush();

            int character = reader.read();
            if (character == '<') {
                while (!xmlResponse.contains("</stream:features>")) {
                    System.out.println(xmlResponse);
                    xmlResponse += (char) character;
                    character = reader.read();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new AuthResponse(xmlResponse + "</stream:stream>");
    }
}
