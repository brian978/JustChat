package com.justchat.gui.panel;

import com.justchat.gui.feedback.ServerStatus;
import com.justchat.gui.form.ConfigForm;

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
    protected ConfigForm configForm;
    protected ServerStatus serverStatus;

    public ActionsPanel(ConfigForm configForm, ServerStatus serverStatus)
    {
        super();

        this.configForm = configForm;
        this.serverStatus = serverStatus;
    }

    public ActionsPanel render()
    {
        if(configForm.collectData().getContainer().size() > 0) {
            serverStatus.append("Configuration loaded");
        }

        this.addLabels();
        this.addButtons();
        this.attachListeners();

        return this;
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
        final ConfigForm configForm = this.configForm;
        final ServerStatus serverStatus = this.serverStatus;

        startServerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                serverStatus.append("Server started");
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
                    configForm.collectData().getContainer().store();
                    configSaved = true;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                serverStatus.append("Server stopped");

                if(configSaved) {
                    serverStatus.append("Configuration saved");
                }

                startServerBtn.setEnabled(true);
                stopServerBtn.setEnabled(false);
            }
        });
    }
}
