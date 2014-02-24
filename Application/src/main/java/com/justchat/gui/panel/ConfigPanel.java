package com.justchat.gui.panel;

import com.justchat.gui.form.ConfigForm;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConfigPanel extends AbstractPanel
{
    protected ConfigForm configForm;

    public ConfigPanel()
    {
        super();
        addLabels();
        buildForm();
    }

    protected void addLabels()
    {
        GridBagConstraints c;

        // Adding the label for the section
        JLabel actionLabel = new JLabel("Server config");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(actionLabel, c);
    }

    protected void buildForm()
    {
        configForm = new ConfigForm();
        configForm.create();

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        add(configForm, c);
    }

    public ConfigForm getForm()
    {
        return configForm;
    }
}
