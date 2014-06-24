package com.acamar.mvc.view;

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
}
