package justchat.gui;

import justchat.gui.panel.ActionsPanel;
import justchat.gui.panel.ConfigPanel;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class MainFrame extends JFrame
{
    public MainFrame(String title)
    {
        super(title);

        ConfigPanel configPanel = new ConfigPanel();
        ActionsPanel actionsPanel = new ActionsPanel(configPanel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1));
        this.add(configPanel);
        this.add(actionsPanel);
        this.setVisible(true);
        this.pack();
        this.validate();

        Dimension dim = this.getSize();
        this.setMinimumSize(dim);
    }
}