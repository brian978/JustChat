package justchat.gui.panel;

import justchat.gui.form.ServerProperties;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ConfigPanel extends JPanel
{
    protected ServerProperties configForm;

    public ConfigPanel()
    {
        setLayout(new GridBagLayout());
        addLabels();
        buildForm();
    }

    protected void addLabels()
    {
        GridBagConstraints c;

        JLabel label = new JLabel("Server properties");
        label.setHorizontalAlignment(SwingConstants.LEFT);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);

        add(label, c);
    }

    protected void buildForm()
    {
        configForm = new ServerProperties(this, 0, 1);
        configForm.createInputForm();
    }

    public Properties getConfig()
    {
        return configForm.getProperties();
    }
}
