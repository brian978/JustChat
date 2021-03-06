package com.acamar.mvc.view;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-24
 */
public abstract class ViewContainer<T> extends Container
{
    /**
     * Returns the view container which keeps all the components
     *
     * @return T
     */
    public abstract T getViewContainer();

    /**
     * Searches, by name, for a component in the given container (shallow search)
     *
     * @param name      Name of the component to locate
     * @return Component|null
     */
    public Component findComponent(String name)
    {
        // We get the container in which to search
        Container container = (Container) getViewContainer();
        if (container instanceof JFrame) {
            container = ((JFrame) container).getContentPane();
        }

        String componentName;
        Component[] components = container.getComponents();

        // Doing a shallow search for a component that has the requested name
        for (Component component : components) {
            componentName = component.getName();
            if (componentName != null && componentName.equals(name)) {
                return component;
            }
        }

        return null;
    }
}
