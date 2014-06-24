package com.acamar.mvc.view;

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
public abstract class AbstractFrame extends ViewContainer<JFrame>
{
    protected JFrame container = new JFrame();

    /**
     * Creates the object, sets the title of the frame and makes sure that the frame is no smaller then is should
     *
     * @param title Title of the frame
     */
    public AbstractFrame(String title)
    {
        container.setTitle(title);

        ensureMinimumSize();
    }

    /**
     * Returns the view container which keeps all the components
     *
     * @return JFrame
     */
    @Override
    public JFrame getViewContainer()
    {
        return container;
    }

    /**
     * The method is a shortcut to the add() method from the container
     *
     * @param panel The panel to be added to the frame
     */
    public void addPanel(AbstractPanel panel)
    {
        container.add(panel.getViewContainer());
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     *
     */
    protected void configure()
    {
        container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        container.setLayout(new GridBagLayout());
        container.setIconImage(new ImageIcon(getClass().getResource("/com/justchat/client/logo/justchat.png")).getImage());
    }

    /**
     * The method is used to show the frame, pack it to it's minimum size (considering the elements on it)
     * and set the location of the frame (which by default will be centered)
     *
     */
    public void display()
    {
        container.setVisible(true);
        container.pack();
        container.setLocationRelativeTo(null);
        container.validate();
    }

    /**
     * Sets the minimum size of the frame
     *
     */
    protected void ensureMinimumSize()
    {
        container.setMinimumSize(container.getSize());
    }

    /**
     * Dispatches a window closing event when, for example, the user clicks an "Exit" button
     *
     */
    public void triggerClosingEvent()
    {
        container.dispatchEvent(new WindowEvent(container, WindowEvent.WINDOW_CLOSING));
    }
}
