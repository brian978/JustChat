package com.justchat.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ActionsPanel extends AbstractPanel
{
    protected JButton startServerBtn;
    protected JButton stopServerBtn;
    protected ConfigPanel configPanel;
    protected StatusPanel statusPanel;

    public ActionsPanel(ConfigPanel configPanel, StatusPanel statusPanel)
    {
        super();
        this.configPanel = configPanel;
        this.statusPanel = statusPanel;
        this.addLabels();
        this.addButtons();
        this.attachListeners();

        if(configPanel.getForm().getConfig().size() > 0) {
            statusPanel.appendMessage("Configuration loaded");
        }
    }

    protected void addLabels()
    {
        GridBagConstraints c;

        // Adding the label for the section
        JLabel actionLabel = new JLabel("Server control");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.insets = new Insets(20, 0, 0, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        add(actionLabel, c);
    }

    protected void addButtons()
    {
        GridBagConstraints c;
        Insets insets = new Insets(10, 10, 10, 10);

        // Creating the start server button
        startServerBtn = new JButton("Start server");
        startServerBtn.setActionCommand("startServer");

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = insets;

        add(startServerBtn, c);

        // Creating the stop server button
        stopServerBtn = new JButton("Stop server");
        stopServerBtn.setActionCommand("stopServer");
        stopServerBtn.setEnabled(false);

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = insets;

        add(stopServerBtn, c);
    }

    protected void attachListeners()
    {
        final ConfigPanel configPanel = this.configPanel;
        final StatusPanel statusPanel = this.statusPanel;

        startServerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                statusPanel.appendMessage("Server started");
                startServerBtn.setEnabled(false);
                stopServerBtn.setEnabled(true);
            }
        });

        stopServerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean configSaved = false;

                try {
                    configPanel.getForm().getData().getConfig().store();
                    configSaved = true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                statusPanel.appendMessage("Server stopped");

                if(configSaved) {
                    statusPanel.appendMessage("Configuration saved");
                }

                startServerBtn.setEnabled(true);
                stopServerBtn.setEnabled(false);
            }
        });
    }
}
