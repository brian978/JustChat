package com.justchat.gui.frame;

import com.justchat.client.frame.FrameInterface;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
abstract public class AbstractFrame extends JFrame implements FrameInterface
{
    public AbstractFrame(String title)
    {
        super(title);
    }

    protected void configureFrame()
    {
        // Configuring the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    abstract protected void populateFrame();

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

    public Component findComponent(String name)
    {
        Component[] components = getContentPane().getComponents();

        String componentName;
        for(Component component : components) {
            componentName = component.getName();
            if(componentName != null && componentName.equals(name)) {
                return component;
            }
        }

        return null;
    }
}
