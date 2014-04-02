package com.acamar.service.provider.facebook.authentication;

import java.io.File;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class LoginRequest extends AuthRequest
{
    File authXml = new File("xml/auth_plain.xml");

    public LoginRequest(String identity, char[] password)
    {
        System.out.println("XML: " + authXml.exists());
    }
}
