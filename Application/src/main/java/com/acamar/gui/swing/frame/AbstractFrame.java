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

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     *
     */
    protected void configureFrame()
    {
        // Configuring the frame
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    /**
     * The method is used to show the frame, pack it to it's minimum size (considering the elements on it)
     * and set the location of the frame
     *
     */
    public void showFrame()
    {
        // Activating the frame
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        validate();
    }

    /**
     * Sets the minimum size of the frame
     *
     */
    protected void ensureMinimumSize()
    {
        setMinimumSize(getSize());
    }

    /**
     * Searches, by name, for a component in the current frame (does not search in the panels of the frame)
     *
     * @param name Name of the component to locate
     * @return Component
     */
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

    /**
     * Dispatches a window closing event when, for example, the user clicks an "Exit" button
     *
     */
    public void triggerClosingEvent()
    {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
