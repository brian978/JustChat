package com.justchat.client.gui.panel.components;

import com.justchat.client.identity.User;
import com.justchat.gui.panel.AbstractPanel;
import sun.awt.VariableGridLayout;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ChatBox extends JTextPane
{
    public ChatBox()
    {
        setName("ChatBox");
        setBackground(Color.WHITE);
        setEditable(false);
    }

    public JScrollPane getScrollable()
    {
        JScrollPane pane = new JScrollPane(this);
        pane.setPreferredSize(new Dimension(300, 200));

        return pane;
    }

    public void append(Color color, User from, String message)
    {
        StyleContext styleContext = StyleContext.getDefaultStyleContext();
        StyledDocument doc = getStyledDocument();
        AttributeSet attributeSet;

        try {
            // Inserting the sender
            attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
            attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, true);
            doc.insertString(doc.getLength(), from.getUsername() + ": ", attributeSet);

            // Inserting the message
            styleContext = StyleContext.getDefaultStyleContext();
            attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
            doc.insertString(doc.getLength(), message + "\r\n", attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
