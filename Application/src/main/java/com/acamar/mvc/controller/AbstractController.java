package com.acamar.mvc.controller;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class AbstractController
{
    /**
     * Searches, by name, for a component in the current frame (does not search in the panels of the frame)
     *
     * @param container The container in which to search for a component
     * @param name      Name of the component to locate
     * @return Component|null
     */
    public Component findComponent(Container container, String name)
    {
        if (container instanceof JFrame) {
            container = ((JFrame) container).getContentPane();
        }

        String componentName;
        Component[] components = container.getComponents();

        for (Component component : components) {
            componentName = component.getName();
            if (componentName != null && componentName.equals(name)) {
                return component;
            }
        }

        return null;
    }
}
