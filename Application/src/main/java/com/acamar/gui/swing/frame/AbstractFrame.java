package com.acamar.gui.swing.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
abstract public class AbstractFrame extends JFrame implements FrameInterface
{
    public AbstractFrame(String title)
    {
        super(title);

        ensureMinimumSize();
    }

    protected void configureFrame()
    {
        // Configuring the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    public void showFrame()
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

    @Override
    public Component findComponent(String name)
    {
        Component[] components = getContentPane().getComponents();

        String componentName;
        for (Component component : components) {
            componentName = component.getName();
            if (componentName != null && componentName.equals(name)) {
                return component;
            }
        }

        return null;
    }

    public void triggerClosingEvent()
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public Window getParentContainer()
    {
        return SwingUtilities.getWindowAncestor(this);
    }
}
