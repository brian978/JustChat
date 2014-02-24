package com.justchat.gui.feedback;

import javax.swing.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ServerStatus extends JTextArea
{
    public ServerStatus()
    {
        super();
        setEditable(false);
    }

    public void append(String text)
    {
        super.append(text + "\r\n");
    }

    public JScrollPane getScrollable()
    {
        JScrollPane areaScrollPane = new JScrollPane(this);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        return areaScrollPane;
    }
}
