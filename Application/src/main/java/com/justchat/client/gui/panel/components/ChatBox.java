package com.justchat.client.gui.panel.components;

import com.acamar.users.User;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-02
 */
public class ChatBox extends JTextPane
{
    StyledDocument doc = getStyledDocument();
    JScrollPane scrollPane = null;

    public ChatBox()
    {
        setName("ChatBox");
        setBackground(Color.WHITE);
        setEditable(false);
    }

    public JScrollPane getScrollable()
    {
        if (scrollPane == null) {
            scrollPane = new JScrollPane(this);
            scrollPane.setPreferredSize(new Dimension(300, 200));
        }

        return scrollPane;
    }

    public void append(Color color, User from, String message)
    {
        AttributeSet attributeSet;

        try {
            // Inserting the sender
            StyleContext styleContext = StyleContext.getDefaultStyleContext();
            attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
            attributeSet = styleContext.addAttribute(attributeSet, StyleConstants.Bold, true);
            doc.insertString(doc.getLength(), from.getName() + ": ", attributeSet);

            // Inserting the message
            // StyleContext needs to be reset or else we'll have mixed styles
            styleContext = StyleContext.getDefaultStyleContext();
            attributeSet = styleContext.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
            doc.insertString(doc.getLength(), message + "\r\n", attributeSet);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        int scrollHeight = scrollPane.getHeight();
        int chatHeight = this.getHeight();

        if (chatHeight > scrollHeight) {
            final ChatBox chatBox = this;
            final Rectangle visibleRect = this.getVisibleRect();
            visibleRect.y = (this.getHeight() - scrollPane.getHeight()) + 19;

            SwingUtilities.invokeLater(new Runnable()
            {
                @Override
                public void run()
                {
                    chatBox.scrollRectToVisible(visibleRect);
                }
            });
        }
    }
}
