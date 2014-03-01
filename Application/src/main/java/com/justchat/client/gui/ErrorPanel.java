package com.justchat.client.gui;

import com.justchat.gui.panel.AbstractPanel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ErrorPanel extends AbstractPanel
{
    String message;

    public ErrorPanel(String message)
    {
        super();

        this.message = message;

        configure();
        populate();
    }

    @Override
    protected void configure()
    {
        super.configure();
    }

    @Override
    protected void populate()
    {
        super.populate();

        GridBagConstraints c;

        JLabel errorMessage = new JLabel(this.message);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 20, 20, 20);
        add(errorMessage, c);
    }
}
