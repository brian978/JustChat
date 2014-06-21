package com.acamar.mvc.view.frame;

import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 */
public interface FrameInterface
{
    /**
     * Searches, by name, for a component in the current frame (does not search in the panels of the frame)
     *
     * @param name Name of the component to locate
     * @return Component
     */
    public Component findComponent(String name);
}
