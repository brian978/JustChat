package com.justchat.client.gui.panel;

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
public class LoginPanel extends AbstractPanel
{
    public LoginPanel()
    {
        super();

        configure();
        populate();
        setupEvents();
    }

    protected void populate()
    {
        super.populate();

        GridBagConstraints c;
        Insets insets = new Insets(15, 10, 5, 0);

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.insets = insets;
        c.anchor = GridBagConstraints.LINE_START;

        /**
         * -----------------
         * Username / email
         * -----------------
         */
        JLabel identityLabel = new JLabel("Username / Email:");
        identityLabel.setHorizontalAlignment(SwingConstants.LEFT);

        c.gridx = 0;
        c.gridy = 0;

        add(identityLabel, c);

        JTextField identityField = new JTextField();
        identityField.setAlignmentX(0);
        identityField.setColumns(10);

        c.gridx = 0;
        c.gridy = 1;

        add(identityField, c);

        /**
         * -----------------
         * Password
         * -----------------
         */
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        c.gridx = 0;
        c.gridy = 2;

        add(passwordLabel, c);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField.setColumns(10);

        c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 3;

        add(passwordField, c);
    }

    protected void setupEvents()
    {

    }
}
