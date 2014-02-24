package com.justchat.gui.panel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public abstract class AbstractPanel extends JPanel
{
    public AbstractPanel()
    {
        super();
        setLayout(new GridBagLayout());
    }
}
