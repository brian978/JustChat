package com.justchat.client.frame.preferences;

import com.acamar.util.Properties;

import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class MainFramePreferences
{
    Properties preferences = new Properties("preferences.properties");

    public MainFramePreferences()
    {
        preferences.checkAndLoad();
    }

    public Dimension getPreferedSize(Dimension defaultSize)
    {
        Object width = preferences.get("MainWidth");
        Object height = preferences.get("MainHeight");

        if (width != null && height != null) {
            return new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        return defaultSize;
    }

    public MainFramePreferences set(String name, String value)
    {
        preferences.setProperty(name, value);

        return this;
    }

    public Properties getStorage()
    {
        return preferences;
    }
}
