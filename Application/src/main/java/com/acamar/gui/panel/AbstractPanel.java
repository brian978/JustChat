package com.acamar.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public abstract class AbstractPanel extends JPanel
{
    public AbstractPanel()
    {
        super();
        setLayout(new GridBagLayout());
    }

    public Component findComponent(String name)
    {
        Component[] components = getComponents();

        String componentName;
        for (Component component : components) {
            componentName = component.getName();
            if (componentName != null && componentName.equals(name)) {
                return component;
            }
        }

        return null;
    }

    public Window getParentContainer()
    {
        return SwingUtilities.getWindowAncestor(this);
    }
}
