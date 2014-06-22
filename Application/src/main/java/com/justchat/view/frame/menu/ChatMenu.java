package com.justchat.view.frame.menu;


import com.acamar.gui.swing.menu.AbstractMenu;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-02
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
