package com.acamar.service.provider.facebook.authentication;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

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

    protected void createDocument(String xml) throws ParserConfigurationException, IOException, SAXException
    {
        document = documentBuilderFactory.newDocumentBuilder()
                                         .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
    }

    protected String converToString() throws TransformerException
    {
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();

        // Trying to make the transformation
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }
}
