package justchat.gui;

import justchat.gui.panel.ActionsPanel;
import justchat.gui.panel.ConfigPanel;
import justchat.gui.panel.StatusPanel;

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

        // Configuring the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Setting up the config panel
        ConfigPanel configPanel = new ConfigPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(0, 0, 0, 0);
        add(configPanel);

        // Setting up the StatusPanel
        StatusPanel statusPanel = new StatusPanel();
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(20, 0, 0, 0);
        add(statusPanel, c);

        // Setting up the ActionsPanel
        ActionsPanel actionsPanel = new ActionsPanel(configPanel, statusPanel);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20, 0, 0, 0);
        add(actionsPanel, c);

        // Activating the frame
        setVisible(true);
        pack();
        validate();

        Dimension dim = getSize();
        setMinimumSize(dim);
    }
}