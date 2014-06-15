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
    public Connection()
    {
        host = getOption("host", "chat.facebook.com");
        port = Integer.parseInt(getOption("port", "5222"));
        resource = getOption("resource", "Pidgin");
    }

    /**
     * The method is used to create the properties object when the connection object is created
     *
     * @return String
     */
    @Override
    protected String getConfigFilename()
    {
        return "facebook.xmpp.properties";
    }
}
