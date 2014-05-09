package com.justchat.client.gui.panel;

import com.acamar.gui.swing.panel.AbstractPanel;
import com.justchat.client.gui.panel.components.ConnectionItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

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
        JComboBox<ConnectionItem> connectionField = new JComboBox<>();
        connectionField.addItem(new ConnectionItem<>(com.acamar.net.xmpp.Connection.class, "XMPP"));
        connectionField.addItem(new ConnectionItem<>(com.acamar.net.xmpp.facebook.Connection.class, "XMPP (Facebook)"));

        createRow("Connection:", connectionField, "connectionField", fieldMargin);
        createRow("Username / Email:", new JTextField(), "identifierField", fieldMargin);
        createRow("Password:", new JPasswordField(), "passwordField", fieldMargin);
        createRow("Server:", new JTextField(), "serverField", fieldMargin);
        createRow("Port:", new JTextField(), "portField", fieldMargin);
        createRow("Resource:", new JTextField(), "resourceField", fieldMargin);

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

    private void createRow(String label, JComponent fieldObject, String fieldName, Insets fieldMargin)
    {
        add(new JLabel(label));
        addBoxSeparator(fieldSeparator);

        add(configureStandardField(fieldObject, fieldName, fieldMargin));
        addBoxSeparator(sectionSeparator);
    }

    private JComponent configureStandardField(JComponent field, String name, Insets margin)
    {
        field.setName(name);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        if (field instanceof JTextField) {
            ((JTextField) field).setColumns(10);
            ((JTextField) field).setMargin(margin);
        }

        // This must be done after we know the number of columns of the field (if any)
        field.setPreferredSize(new Dimension(100, field.getPreferredSize().height));

        return field;
    }

    public void prefill(HashMap<String, String> data)
    {
        Component component;
        Set<String> keys = data.keySet();

        for (String key : keys) {
            component = findComponent(key);
            if (component != null) {
                if (component instanceof JTextField) {
                    ((JTextField) component).setText(data.get(key));
                }
            }
        }
    }
}
