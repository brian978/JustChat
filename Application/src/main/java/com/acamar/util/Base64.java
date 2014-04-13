package com.acamar.util;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Base64
{
    public static String encode(String source)
    {
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(source.getBytes());
    }

    public static byte[] decode(String source)
    {
        return com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(source);
    }
}
