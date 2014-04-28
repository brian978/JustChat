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
    final public String defaultResource = "Pidgin";

    public Connection()
    {
        host = getOption("host", defaultHost);
        port = Integer.parseInt(getOption("port", defaultPort));
        resource = getOption("resource", defaultResource);
    }

    @Override
    protected String getConfigFilename()
    {
        return "facebook.xmpp.properties";
    }
}
