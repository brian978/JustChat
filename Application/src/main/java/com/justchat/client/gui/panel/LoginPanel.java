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

        add(identityField, c, 0);

        /**
         * -----------------
         * Password
         * -----------------
         */
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = breakInset;

        add(passwordLabel, c);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField.setColumns(10);

        c.gridx = 0;
        c.gridy = 3;
        c.insets = insets;

        add(passwordField, c, 1);

        /**
         * -----------------
         * Login button
         * -----------------
         */
        JButton loginBtn = new JButton("Login");
        loginBtn.setActionCommand("doLogin");
        loginBtn.setEnabled(false);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = breakInset;

        add(loginBtn, c, 2);
    }

    protected void setupEvents()
    {
        final JTextField identifier = (JTextField) getComponent(0);
        final JPasswordField password = (JPasswordField) getComponent(1);

        JButton loginBtn = (JButton) getComponent(2);
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
