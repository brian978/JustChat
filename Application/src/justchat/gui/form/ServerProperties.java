package justchat.gui.form;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ServerProperties
{
    Insets insets = new Insets(5, 5, 5, 5);

    ArrayList<TextField> fields = new ArrayList<>();

    JPanel panel;

    int startGridX, startGridY;

    public ServerProperties(JPanel panel, int startGridX, int startGridY)
    {
        this.panel = panel;
        this.startGridX = startGridX;
        this.startGridY = startGridY;
    }

    protected void addField(TextField field)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = startGridX;
        c.gridy = startGridY++;
        c.insets = insets;
        fields.add(field);
        panel.add(field, c);
    }

    public void createInputForm()
    {
        addField(new TextField("Host:", "host"));
        addField(new TextField("Port:", "port"));
    }

    public Properties getProperties()
    {
        Properties properties = new Properties();

        for(TextField field : fields) {
            properties.setProperty(field.getIdentifier(), field.getValue());
        }

        return properties;
    }
}
