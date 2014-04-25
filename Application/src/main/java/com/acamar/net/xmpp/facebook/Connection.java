package com.acamar.net.xmpp.facebook;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-18
 */
public class Connection extends com.acamar.net.xmpp.Connection
{
    // Connection defaults
    final public String defaultHost = "chat.facebook.com";
    final public String defaultPort = "5222";

    public Connection()
    {
        setup(getOption("host", defaultHost), Integer.parseInt(getOption("port", defaultPort)));
    }

    @Override
    protected String getConfigFilename()
    {
        return "facebook.xmpp.properties";
    }
}
