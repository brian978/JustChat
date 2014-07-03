package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.event.EventInterface;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.util.Properties;
import com.acamar.util.PropertiesAwareInterface;
import com.justchat.mvc.view.frame.Contacts;

import javax.swing.*;
import java.awt.*;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class ContactsController extends AbstractController implements PropertiesAwareInterface
{
    Properties settings = null;
    Contacts view = new Contacts();

    /**
     * The method will be called after all the dependencies have been injected in the controller
     */
    @Override
    public void completeSetup()
    {
        if (!setupCompleted) {
            view.getUsersPanel()
                .setEventManager(eventManager);

            setViewPreferredSize();
            attachEvents();
        }
    }

    /**
     * Sets the preferred size of the view
     *
     */
    private void setViewPreferredSize()
    {
        JFrame container = view.getViewContainer();
        Dimension preferredSize = container.getPreferredSize();
        Dimension size = container.getSize();
        Object width = settings.get("ContactsWidth", String.valueOf((int) size.getWidth()));
        Object height = settings.get("ContactsHeight", String.valueOf((int) size.getHeight()));

        if (width != null && height != null) {
            preferredSize = new Dimension(Integer.parseInt(width.toString()), Integer.parseInt(height.toString()));
        }

        container.setPreferredSize(preferredSize);
    }

    /**
     * Attached the events for which the controller or a nested object will listen to
     *
     */
    private void attachEvents()
    {
        eventManager.attach(AuthenticationEvent.class, new AuthenticationEventsListener());
    }

    /**
     * Injects a properties object
     *
     * @param properties Properties to be injected
     */
    @Override
    public void setProperties(Properties properties)
    {
        settings = properties;
    }

    /**
     * @return A Properties object
     */
    @Override
    public Properties getProperties()
    {
        return settings;
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-03
     */
    private class AuthenticationEventsListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         * It will update the login fields
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            // If the login is successful we hide the current frame (since we don't need it for now)
            if (((AuthenticationEvent) e).getStatusCode() == AuthenticationEvent.StatusCode.SUCCESS) {
                view.display();
            }
        }
    }
}
