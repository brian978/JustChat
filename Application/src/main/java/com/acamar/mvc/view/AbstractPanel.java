package com.acamar.mvc.view;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 2.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-03
 */
public abstract class AbstractPanel extends ViewContainer<JPanel>
{
    protected JPanel container = new JPanel();

    /**
     * Creates the object and sets the default layout for a panel
     *
     */
    public AbstractPanel()
    {
        container.setLayout(new GridBagLayout());
    }

    /**
     * Returns the view container which keeps all the components
     *
     * @return JPanel
     */
    @Override
    public JPanel getViewContainer()
    {
        return container;
    }

    /**
     * Adds a box separator in the container
     *
     * @param size A dimension object that will be used to create an empty box area
     */
    protected void addBoxSeparator(Dimension size)
    {
        container.add(Box.createRigidArea(size));
    }
}
