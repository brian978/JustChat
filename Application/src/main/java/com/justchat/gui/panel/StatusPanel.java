package com.justchat.gui.panel;

import com.justchat.gui.feedback.ServerStatus;
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
    protected ServerStatus serverStatus;

    public StatusPanel(ServerStatus serverStatus)
    {
        super();

        this.serverStatus = serverStatus;

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

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;

        add(serverStatus.getScrollable(), c);
    }
}
