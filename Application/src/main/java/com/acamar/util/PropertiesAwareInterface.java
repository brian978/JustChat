package com.acamar.util;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-07-03
 */
public interface PropertiesAwareInterface
{
    /**
     * Injects a properties object
     *
     * @param properties Properties to be injected
     */
    public void setProperties(Properties properties);

    /**
     *
     * @return A Properties object
     */
    public Properties getProperties();
}
