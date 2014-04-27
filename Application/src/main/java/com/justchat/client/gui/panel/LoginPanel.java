package com.justchat.client.gui.panel;

import com.acamar.gui.swing.panel.AbstractPanel;

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
    Dimension sectionSeparator = new Dimension(0, 20);
    Dimension fieldSeparator = new Dimension(0, 7);
    Insets fieldMargin = new Insets(3, 3, 3, 3);

    public LoginPanel()
    {
        super();

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        populate();
    }

    protected void populate()
    {
        addBoxSeparator(new Dimension(0, 10));

        /**
         * -----------------
         * Fields
         * -----------------
         */
        createRow("Username / Email:", new JTextField(), "identifierField", fieldMargin);
        createRow("Password:", new JPasswordField(), "passwordField", fieldMargin);
        createRow("Server:", new JTextField(), "serverField", fieldMargin);
        createRow("Port:", new JTextField(), "portField", fieldMargin);

        /**
         * -----------------
         * Login button
         * -----------------
         */
        JButton loginBtn = new JButton("Login");
        loginBtn.setActionCommand("doLogin");
        loginBtn.setEnabled(true);
        loginBtn.setName("loginBtn");

        add(loginBtn);
    }

    private void createRow(String label, JTextField fieldObject, String fieldName, Insets fieldMargin)
    {
        add(new JLabel(label));
        addBoxSeparator(fieldSeparator);

        add(configureStandardField(fieldObject, fieldName, fieldMargin));
        addBoxSeparator(sectionSeparator);
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
}
