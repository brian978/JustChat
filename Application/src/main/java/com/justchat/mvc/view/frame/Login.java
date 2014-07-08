package com.justchat.mvc.view.frame;

import com.acamar.authentication.AbstractAuthentication;
import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventManager;
import com.acamar.net.xmpp.Connection;
import com.acamar.util.Properties;
import com.justchat.mvc.view.frame.menu.MainMenu;
import com.justchat.mvc.view.panel.AuthenticatePanel;
import com.justchat.mvc.view.panel.LoginPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-04-16
 */
public class Login extends AbstractMainFrame
{
    // Frame components
    private LoginPanel loginPanel = new LoginPanel();
    private AuthenticatePanel authenticatePanel = new AuthenticatePanel();

    /**
     * Creates a login frame object
     *
     */
    public Login()
    {
        super("JustChat");
    }

    /**
     * Returns the login panel object
     *
     * @return LoginPanel
     */
    public LoginPanel getLoginPanel()
    {
        return loginPanel;
    }

    /**
     * Returns the authentication panel object
     *
     * @return AuthenticationPanel
     */
    public AuthenticatePanel getAuthenticatePanel()
    {
        return authenticatePanel;
    }

    /**
     * Injects an EventManager object into another object
     *
     * @param eventManager An EventManager object
     */
    @Override
    public void setEventManager(EventManager eventManager)
    {
        super.setEventManager(eventManager);

        loginPanel.setEventManager(eventManager);
    }

    /**
     * Sets different properties of the frame, what layout to use and what happens when the frame is closed
     *
     */
    @Override
    protected void configure()
    {
        super.configure();

        container.setLayout(new BoxLayout(container.getContentPane(), BoxLayout.PAGE_AXIS));
    }

    @Override
    public AbstractMainFrame initialize()
    {
        super.initialize();

        loginPanel.populate();
        authenticatePanel.populate();

        return this;
    }

    /**
     * Sets the minimum size of the frame
     *
     */
    @Override
    protected void ensureMinimumSize()
    {
        container.setMinimumSize(new Dimension(200, 400));
    }

    /**
     * Adds the default elements that are on the frame (other can be added dynamically any other time of course)
     *
     */
    @Override
    protected void populateFrame()
    {
        /**
         * -------------
         * main menu
         * -------------
         */
        container.setJMenuBar(menu.getContainer());
        menu.display(MainMenu.DEFAULT_ITEMS);

        /**
         * -------------
         * Login panel
         * -------------
         */
        loginPanel.setName("loginPanel");
        container.add(loginPanel.getViewContainer());

        /**
         * -------------
         * Authenticate panel
         * -------------
         */
        authenticatePanel.setName("authenticatePanel");
        authenticatePanel.getViewContainer().setVisible(false);
        container.add(authenticatePanel.getViewContainer());
    }

    /**
     * The method switches between the authentication and the login panel
     *
     */
    public void toggleMainPanels()
    {
        JPanel loginContainer = loginPanel.getViewContainer();
        JPanel authContainer = authenticatePanel.getViewContainer();

        loginContainer.setVisible(!loginContainer.isVisible());
        authContainer.setVisible(!authContainer.isVisible());

        JButton cancelBtn = (JButton) authenticatePanel.findComponent("cancelBtn");
        cancelBtn.setEnabled(true);
    }

    /**
     * It handles the first step before the data preparation for the login panel which is the extraction of the
     * connection object from the service item
     *
     * @param serviceItem CommunicationServiceItem
     */
    public void prefillData(CommunicationServiceItem serviceItem)
    {
        Authentication authentication = null;

        try {
            authentication = serviceItem.getInstance();
        } catch (IllegalAccessException | InstantiationException e1) {
            e1.printStackTrace();
        }

        if (authentication != null) {
            prefillData(authentication.getConnection());
        }
    }

    /**
     * Prepares the data from the connection object for the login panel that will add it to the UI
     *
     * @param connection Connection
     */
    public void prefillData(Connection connection)
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("serverField", connection.getHost());
        data.put("portField", String.valueOf(connection.getPort()));
        data.put("resourceField", connection.getResource());

        loginPanel.prefill(data);
    }
}
