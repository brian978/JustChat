package justchat.gui.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ActionsPanel extends JPanel
{
    protected JButton startServerBtn;
    protected JButton stopServerBtn;
    protected ConfigPanel configPanel;

    public ActionsPanel(ConfigPanel configPanel)
    {
        super();
        this.configPanel = configPanel;
        this.setLayout(new GridBagLayout());
        this.addLabels();
        this.addButtons();
        this.attachListeners();
    }

    protected void addLabels()
    {
        GridBagConstraints c;

        JLabel actionLabel = new JLabel("Server control");

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;

        this.add(actionLabel, c);
    }

    protected void addButtons()
    {
        GridBagConstraints c;
        Insets insets = new Insets(10, 10, 10, 10);

        // Creating the start server button
        this.startServerBtn = new JButton("Start server");
        this.startServerBtn.setActionCommand("startServer");

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.insets = insets;

        this.add(this.startServerBtn, c);

        // Creating the stop server button
        this.stopServerBtn = new JButton("Stop server");
        this.stopServerBtn.setActionCommand("stopServer");

        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.insets = insets;

        this.add(this.stopServerBtn, c);
    }

    protected void attachListeners()
    {
        final ConfigPanel configPanel = this.configPanel;

        startServerBtn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                boolean fileExists = false;

                File file = new File("config.properties");
                fileExists = file.exists();

                if(fileExists == false) {
                    try {
                        fileExists = file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if(fileExists) {
                    OutputStream out = null;
                    try {
                        out = new FileOutputStream(file);
                        Properties properties = configPanel.getConfig();
                        properties.store(out, "Server properties");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }
}
