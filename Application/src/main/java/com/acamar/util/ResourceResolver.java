package com.acamar.util;

import java.net.URL;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class ResourceResolver
{
    public static URL getResource(Class object, String relativePath)
    {
        return object.getClassLoader().getResource(object.getPackage().getName().replace('.', '/') + relativePath);
    }
}
