package com.justchat.mvc.view.panel;

import com.acamar.mvc.view.AbstractPanel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-26
 */
public class AuthenticatePanel extends AbstractPanel
{
    /**
     * Creates the panel, configures it and then populates with the required components
     *
     */
    public AuthenticatePanel()
    {
        super();

        container.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        container.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        container.setAlignmentX(Component.CENTER_ALIGNMENT);

        populate();
    }

    /**
     * Populates the panel with it's objects
     *
     */
    private void populate()
    {
        addBoxSeparator(new Dimension(0, 20));

        // Informational label
        JLabel label = new JLabel("Authenticating, please wait...");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(label);

        addBoxSeparator(new Dimension(0, 20));

        // Cancel button
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setName("cancelBtn");
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(cancelBtn);
    }
}
