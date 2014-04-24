package com.justchat.client.frame.menu;


import com.acamar.gui.swing.menu.AbstractMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * JustChat
 *
 * @link https://github.com/brian978/JustChat
 * @copyright Copyright (c) 2014
 * @license Creative Commons Attribution-ShareAlike 3.0
 */
public class ChatMenu extends AbstractMenu
{
    public ChatMenu()
    {
        JMenu fileMenu = addMenu("File", "fileMenu");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        addMenuItem(fileMenu, "Send file", "sendFileItem");

        fileMenu.addSeparator();

        addMenuItem(fileMenu, "Close", "conversationCloseItem");
    }
}
