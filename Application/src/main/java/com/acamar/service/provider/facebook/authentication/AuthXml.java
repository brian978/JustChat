package com.acamar.service.provider.facebook.authentication;

import com.acamar.util.ResourceResolver;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
abstract public class AuthXml
{
    protected DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    protected Document document = null;
    protected File xml = null;

    public AuthXml()
    {

    }

    public void loadXml(String xmlName)
    {
        URL resource = ResourceResolver.getResource(AuthRequest.class, "/xml/" + xmlName + ".xml");
        xml = new File(resource.getPath());
    }

    abstract public void send();
}
