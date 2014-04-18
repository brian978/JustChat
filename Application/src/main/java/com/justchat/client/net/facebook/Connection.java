package com.justchat.client.net.facebook;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-18
 */
public class Connection extends com.acamar.net.xmpp.Connection
{
    public Connection()
    {
        // We need the filename so we can get the options
        configFilename = "facebook.xmpp.properties";

        setup(null, getOption("host", "chat.facebook.com"), Integer.parseInt(getOption("port", "5222")));
    }
}
