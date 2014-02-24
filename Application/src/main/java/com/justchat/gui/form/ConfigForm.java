package com.justchat.gui.form;

import com.justchat.gui.form.field.TextField;
import com.justchat.util.PropertiesHandler;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConfigForm extends JPanel
{
    Insets insets = new Insets(5, 5, 5, 5);
    ArrayList<TextField> fields = new ArrayList<>();
    PropertiesHandler config = new PropertiesHandler("servers.config");
    boolean created = false;

    public ConfigForm()
    {
        setLayout(new GridBagLayout());
        setBackground(new Color(0, 0, 0, 0));

        // Adding some default fields to the form
        addField(new TextField("Port:", "port"));
    }

    public void addField(TextField field)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = fields.size() - 1;
        c.insets = insets;
        fields.add(field);
        add(field, c);
    }

    public ConfigForm create()
    {
        // We don't want to create this form twice
        if(created) {
            return this;
        }

        try {
            config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Populating the fields with data
        for(TextField field : fields) {
            field.setValue(config.getProperty(field.getIdentifier()));
        }

        created = true;

        return this;
    }

    public ConfigForm collectData()
    {
        // Populating the fields with data
        for(TextField field : fields) {
            config.setProperty(field.getIdentifier(), field.getValue());
        }

        return this;
    }

    public PropertiesHandler getContainer()
    {
        return config;
    }
}
