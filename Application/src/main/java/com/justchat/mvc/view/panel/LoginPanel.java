package com.justchat.mvc.view.panel;

import com.acamar.mvc.view.AbstractPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Set;

/**
 * JustChat
 *
 * @version 1.2
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-03
 */
public class LoginPanel extends AbstractPanel
{
    // Triggered events
    public final static String EVENT_SERVICE_CHANGED = "communication.service.changed";

    // Panel properties
    Dimension sectionSeparator = new Dimension(0, 20);
    Dimension fieldSeparator = new Dimension(0, 7);
    Insets fieldMargin = new Insets(3, 3, 3, 3);

    /**
     * Creates the panel, configures it and then populates with the required components
     *
     */
    public LoginPanel()
    {
        super();

        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        populate();
    }

    /**
     * Populates the panel with it's objects
     *
     */
    protected void populate()
    {
        addBoxSeparator(new Dimension(0, 10));

        /**
         * -----------------
         * Fields
         * -----------------
         */
        JComboBox<CommunicationServiceItem> connectionField = new JComboBox<>();
        connectionField.addItem(new CommunicationServiceItem<>(com.acamar.authentication.xmpp.Authentication.class, "XMPP"));
        connectionField.addItem(new CommunicationServiceItem<>(com.acamar.authentication.xmpp.facebook.Authentication.class, "XMPP (Facebook)"));

        createRow("Connection:", connectionField, "connectionField", fieldMargin);
        createRow("Username / Email:", new JTextField(), "identifierField", fieldMargin);
        createRow("Password:", new JPasswordField(), "passwordField", fieldMargin);
        createRow("Server:", new JTextField(), "serverField", fieldMargin);
        createRow("Port:", new JTextField(), "portField", fieldMargin);
        createRow("Resource:", new JTextField(), "resourceField", fieldMargin);

        /**
         * -----------------
         * Remember login
         * -----------------
         */
        JCheckBox rememberLogin = new JCheckBox("Remember my ID & password");
        rememberLogin.setActionCommand("rememberLogin");
        rememberLogin.setName("rememberLogin");
        container.add(rememberLogin);
        addBoxSeparator(sectionSeparator);

        /**
         * -----------------
         * Login button
         * -----------------
         */
        JButton loginBtn = new JButton("Login");
        loginBtn.setActionCommand("doLogin");
        loginBtn.setEnabled(true);
        loginBtn.setName("loginBtn");

        container.add(loginBtn);
    }

    /**
     * Creates a new row
     *
     * @param label Label for the row
     * @param fieldObject Swing component for the row (like a text field)
     * @param fieldName Swing component name. This is used to located the field using the findComponent() method
     * @param fieldMargin Margin around the field
     */
    private void createRow(String label, JComponent fieldObject, String fieldName, Insets fieldMargin)
    {
        container.add(new JLabel(label));
        addBoxSeparator(fieldSeparator);

        container.add(configureStandardField(fieldObject, fieldName, fieldMargin));
        addBoxSeparator(sectionSeparator);
    }

    /**
     * Configures a standard field (usually called by the createRow() method)
     *
     * @param field Swing component to be configured
     * @param name Swing component name. This is used to located the field using the findComponent() method
     * @param margin Margin around the field
     * @return JComponent
     */
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

    /**
     * Adds a set of data to the fields present in the panel
     *
     * Each entry in the data must identified by the name of the element in the panel that must be
     * populated with that data
     *
     * @param data HashMap<String, String>
     */
    public void prefill(HashMap<String, String> data)
    {
        Component component;
        Set<String> keys = data.keySet();

        for (String key : keys) {
            component = this.findComponent(key);
            if (component != null) {
                if (component instanceof JTextField) {
                    ((JTextField) component).setText(data.get(key));
                }
            }
        }
    }
}
