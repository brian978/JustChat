package com.justchat.client.gui.panel;

import com.justchat.service.AuthenticationInterface;
import com.justchat.gui.panel.AbstractPanel;

import javax.swing.*;
import javax.swing.text.JTextComponent;
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
    AuthenticationInterface authentication;

    public LoginPanel(AuthenticationInterface authentication)
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.authentication = authentication;

        configure();
        populate();
    }

    private void addSeparator(Dimension size)
    {
        add(Box.createRigidArea(size));
    }

    private JTextField configureStandardField(JTextField field, String name, Insets margin)
    {
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setColumns(10);
        field.setMargin(margin);
        field.setMaximumSize(field.getPreferredSize());
        field.setName(name);

        return field;
    }

    protected void populate()
    {
        super.populate();

        Dimension sectionSeparator = new Dimension(0, 20);
        Dimension fieldSeparator = new Dimension(0, 7);
        Insets fieldMargin = new Insets(3, 3, 3, 3);

        /**
         * --------------------
         * Informational label
         * --------------------
         */
        JLabel infoLabel = new JLabel("<html><center>Connecting,<br> please wait...");
        infoLabel.setName("infoLabel");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(infoLabel);
        addSeparator(new Dimension(0, 15));

        /**
         * -----------------
         * Username / email
         * -----------------
         */
        // Label
        JLabel identityLabel = new JLabel("Username / Email:");

        add(identityLabel);
        addSeparator(fieldSeparator);

        // Field
        add(configureStandardField(new JTextField(), "identifierField", fieldMargin));
        addSeparator(sectionSeparator);

        /**
         * -----------------
         * Password
         * -----------------
         */
        // Label
        JLabel passwordLabel = new JLabel("Password:");

        add(passwordLabel);
        addSeparator(fieldSeparator);

        // Field
        add(configureStandardField(new JPasswordField(), "passwordField", fieldMargin));
        addSeparator(sectionSeparator);

        /**
         * -----------------
         * Login button
         * -----------------
         */
        JButton loginBtn = new JButton("Login");
        loginBtn.setActionCommand("doLogin");
        loginBtn.setEnabled(false);
        loginBtn.setName("loginBtn");

        add(loginBtn);
    }
}
