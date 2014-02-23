package justchat.gui.form.field;

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
        setLayout(new GridBagLayout());
        setBackground(new Color(0, 0, 0, 0));

        this.label = new JLabel(text);
        this.field = new JTextField();
        this.identifier = identifier;

        configureLabel();
        configureField();

        Insets insets = new Insets(0, 0, 0, 5);
        GridBagConstraints c;

        // Adding the label to the panel
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 5;
        c.ipady = 5;
        c.insets = insets;
        add(label, c);

        // Adding the field to the panel
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        add(field, c);
    }

    protected void configureLabel()
    {
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setMinimumSize(new Dimension(100, 100));
        label.setLabelFor(field);
        label.setPreferredSize(new Dimension(30, label.getPreferredSize().height));
    }

    protected void configureField()
    {
        field.setColumns(20);
    }

    public String getValue()
    {
        return field.getText();
    }

    public void setValue(String value)
    {
        field.setText(value);
    }

    public String getIdentifier()
    {
        return identifier;
    }
}
