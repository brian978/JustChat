package com.justchat.client.gui.panel;

import com.justchat.service.provider.AuthenticationInterface;
import com.justchat.gui.panel.AbstractPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class LoginPanel extends AbstractPanel
{
    AuthenticationInterface authentication;

    public LoginPanel(AuthenticationInterface authentication)
    {
        super();

        this.authentication = authentication;

        configure();
        populate();
        setupEvents();
    }

    protected void populate()
    {
        super.populate();

        GridBagConstraints c;

        // Some default constraints and insets
        Insets insets = new Insets(0, 10, 5, 0);
        Insets breakInset = new Insets(12, 10, 5, 0);

        /**
         * --------------------
         * Informational label
         * --------------------
         */
        JLabel infoLabel = new JLabel("<html><center>Connecting to <br>messaging server...</center>");
        infoLabel.setName("infoLabel");

        c = new GridBagConstraints();
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 20, 0);

        add(infoLabel, c);

        // Constraints for the fields
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
        c.gridy = 1;

        add(identityLabel, c);

        JTextField identityField = new JTextField();
        identityField.setAlignmentX(0);
        identityField.setColumns(10);
        identityField.setName("identifierField");

        c.gridx = 0;
        c.gridy = 2;

        add(identityField, c);

        /**
         * -----------------
         * Password
         * -----------------
         */
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = breakInset;

        add(passwordLabel, c);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField.setColumns(10);
        passwordField.setName("passwordField");

        c.gridx = 0;
        c.gridy = 4;
        c.insets = insets;

        add(passwordField, c);

        /**
         * -----------------
         * Login button
         * -----------------
         */
        JButton loginBtn = new JButton("Login");
        loginBtn.setActionCommand("doLogin");
        loginBtn.setEnabled(false);
        loginBtn.setName("loginBtn");

        c.gridx = 0;
        c.gridy = 5;
        c.insets = breakInset;

        add(loginBtn, c);
    }

    protected void setupEvents()
    {
        final JTextField identifier = (JTextField) findComponent("identifierField");
        final JPasswordField password = (JPasswordField) findComponent("passwordField");

        JButton loginBtn = (JButton) findComponent("loginBtn");
        loginBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (e.getActionCommand().equals("doLogin")) {
                    authentication.authenticate(identifier.getText(), new String(password.getPassword()));
                }
            }
        });
    }
}
