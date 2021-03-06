package com.acamar.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public class Properties extends java.util.Properties
{
    protected File file;
    protected boolean loaded = false;

    public Properties(String filename)
    {
        this(new File(filename));
    }

    public Properties(File file)
    {
        this.file = file;
    }

    protected void load() throws IOException
    {
        load(new FileInputStream(this.file));
    }

    public boolean checkAndLoad()
    {
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    store();
                    loaded = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                load();
                loaded = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return loaded;
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public Properties set(String name, String value)
    {
        setProperty(name, value);

        return this;
    }

    public String get(String name, String defaultValue)
    {
        if (!containsKey(name) && defaultValue != null) {
            setProperty(name, defaultValue);
        }

        return getProperty(name);
    }

    public void store() throws IOException
    {
        FileOutputStream stream = new FileOutputStream(this.file);
        OutputStreamWriter writer = new OutputStreamWriter(stream, Charset.forName("UTF-8"));
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        store(bufferedWriter, "Generated by Acamar package");
    }
}
