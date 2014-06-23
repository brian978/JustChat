package com.acamar.mvc.view.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

/**
 * JustChat
 *
 * @version 2.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-03
 */
public abstract class AbstractFrame
{
    protected JFrame frame = new JFrame();

    public AbstractFrame(String title)
    {
        frame.setTitle(title);

        ensureMinimumSize();
    }

    /**
     * Returns a JFrame instance
     *
     * @return JFrame
     */
    public JFrame getFrame()
    {
        return frame;
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     */
    protected void configureFrame()
    {
        // Configuring the frame
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    /**
     * The method is used to show the frame, pack it to it's minimum size (considering the elements on it)
     * and set the location of the frame
     */
    public void show()
    {
        // Activating the frame
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.validate();
    }

    /**
     * Sets the minimum size of the frame
     */
    protected void ensureMinimumSize()
    {
        frame.setMinimumSize(frame.getSize());
    }

    /**
     * Searches, by name, for a component in the current frame (does not search in the panels of the frame)
     *
     * @param name Name of the component to locate
     * @return Component
     */
    public Component findComponent(String name)
    {
        Component[] components = frame.getContentPane().getComponents();

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
     */
    public void triggerClosingEvent()
    {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
