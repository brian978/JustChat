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
    }

    @Override
    protected String getConfigFilename()
    {
        return "facebook.xmpp.properties";
    }

    @Override
    protected void setup()
    {
        setup(null, getOption("host", "chat.facebook.com"), Integer.parseInt(getOption("port", "5222")));
    }
}
