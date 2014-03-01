package com.justchat.client;

import com.justchat.client.gui.ChatPanel;
import com.justchat.client.gui.ErrorPanel;

import javax.swing.*;
import javax.websocket.DeploymentException;
import java.awt.*;
import java.io.IOException;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class Main extends JFrame
{
    public static void main(String[] args)
    {
        new Main();
    }

    public Main()
    {
        super("JustChat");
        configureFrame();
        populateFrame();
        showFrame();
        ensureMinimumSize();
    }

    protected void configureFrame()
    {
        // Configuring the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon(getClass().getResource("logo/justchat.png")).getImage());
    }

    protected void showFrame()
    {
        // Activating the frame
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        validate();
    }

    protected void ensureMinimumSize()
    {
        setMinimumSize(getSize());
    }

    protected void populateFrame()
    {
        GridBagConstraints c;

        c = new GridBagConstraints();
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        try {
            ChatPanel chatPanel = new ChatPanel();
            chatPanel.setName("ChatPanel");
            add(chatPanel, c);
        } catch (IOException | DeploymentException e) {
            ErrorPanel errorPanel = new ErrorPanel(e.getMessage());
            add(errorPanel, c);
        }
    }
}
