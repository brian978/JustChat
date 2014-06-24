package com.justchat.mvc.controller;

import com.acamar.authentication.AuthenticationEvent;
import com.acamar.authentication.AuthenticationListener;
import com.acamar.event.Event;
import com.acamar.event.EventInterface;
import com.acamar.event.EventListenerInterface;
import com.acamar.event.EventManager;
import com.acamar.mvc.controller.AbstractController;
import com.justchat.mvc.view.frame.Login;
import com.justchat.mvc.view.panel.LoginPanel;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import java.lang.reflect.Method;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class LoginController extends AbstractController
{
    private EventManager eventManager;
    private Login loginFrame = new Login();

    /**
     * Creates the object and attaches the required events
     *
     * @param eventManager Event manager object
     */
    public LoginController(EventManager eventManager)
    {
        this.eventManager = eventManager;

        attachEvents();
    }

    /**
     * Attaches a set of events to the event listener
     */
    private void attachEvents()
    {
        eventManager.attach(LoginPanel.EVENT_SERVICE_CHANGED, new CommunicationServiceChangedListener());
        eventManager.attach(AuthenticationEvent.class, new AuthenticationEventsListener());
    }

    public void displayLogin()
    {

    }

    /**
     * The event listener handles events triggered by the login panel when the communication service changes
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-24
     */
    private class CommunicationServiceChangedListener implements EventListenerInterface
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
            loginFrame.prefillData((CommunicationServiceItem) e.getParams().get("item"));
        }
    }

    /**
     * The class decides what happens to the frame when an authentication event occurs
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-03-03
     */
    private class AuthenticationEventsListener implements EventListenerInterface
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
                loginFrame.setVisible(false);
            }

            // Since the login was done we need to revert what we show on this frame to the original state
            // in case the user logs out
            loginFrame.toggleMainPanels();
        }
    }
}
