package com.justchat.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class StatusPanel extends AbstractPanel
{
    protected JTextArea statusBox;

    public StatusPanel()
    {
        super();
        addLabels();
        addStatusBox();
    }

    private void addLabels()
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 10, 0);
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        add(new JLabel("Status:"), c);
    }

    protected void addStatusBox()
    {
        GridBagConstraints c;

        statusBox = new JTextArea();
        statusBox.setEditable(false);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;

        JScrollPane areaScrollPane = new JScrollPane(statusBox);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        areaScrollPane.setPreferredSize(new Dimension(300, 200));

        add(areaScrollPane, c);
    }

    public JTextArea getStatusBox()
    {
        return statusBox;
    }

    public StatusPanel appendMessage(String msg)
    {
        statusBox.append(msg + "\r\n");

        return this;
    }
}
