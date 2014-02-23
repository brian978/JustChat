package justchat.gui.form;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class TextField extends JPanel
{
    protected JLabel label;
    protected JTextField field;
    protected String identifier;

    public TextField(String text, String identifier)
    {
        label = new JLabel(text);
        field = new JTextField();
        this.identifier = identifier;

        setLayout(new GridBagLayout());

        // Configuring the label
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setMinimumSize(new Dimension(100, 100));
        label.setLabelFor(field);
        label.setPreferredSize(new Dimension(30, label.getPreferredSize().height));

        // Configuring the text field
        field.setColumns(20);

        Insets insets = new Insets(0, 0, 0, 5);
        GridBagConstraints c = new GridBagConstraints();

        // Standard constraints
        c.gridy = 0;
        c.insets = insets;

        // Adding the label to the panel
        c.gridx = 0;
        c.ipadx = 5;
        c.ipady = 5;
        add(label, c);

        // Adding the field to the panel
        c.gridx = 1;
        add(field, c);
    }

    public JTextField getField()
    {
        return field;
    }

    public String getValue()
    {
        return field.getText();
    }

    public String getIdentifier()
    {
        return identifier;
    }
}
