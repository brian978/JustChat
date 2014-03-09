package com.justchat.model.user.identity;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Token
{
    private String value = "";

    public Token(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
