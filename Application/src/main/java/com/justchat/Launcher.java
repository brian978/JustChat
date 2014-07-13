package com.justchat;

import com.acamar.authentication.xmpp.Authentication;
import com.acamar.event.EventInterface;
import com.acamar.event.EventManager;
import com.acamar.event.listener.AbstractEventListener;
import com.acamar.mvc.controller.AbstractController;
import com.acamar.mvc.event.MvcEvent;
import com.acamar.util.Properties;
import com.acamar.util.PropertiesAwareInterface;
import com.justchat.mvc.controller.ContactsController;
import com.justchat.mvc.controller.ConversationsController;
import com.justchat.mvc.controller.LoginController;
import com.justchat.mvc.view.frame.Contacts;
import com.justchat.mvc.view.frame.Login;
import com.justchat.mvc.view.panel.components.CommunicationServiceItem;

import java.io.IOException;

/**
 * JustChat
 *
 * @version 1.1
 * @link https://github.com/brian978/JustChat
 * @since 2014-03-02
 */
public class Launcher
{
    Properties settings = new Properties("preferences.properties");
    EventManager eventManager = new EventManager();

    /**
     * The method creates a new instance of the Launcher object
     *
     * @param args Arguments for the launcher
     */
    public static void main(String[] args)
    {
        new Launcher();
    }

    /**
     * Creates a launcher object and configures it
     */
    public Launcher()
    {
        // Loading (or creating) the preferences file
        // TODO: handle read/create file failed (maybe with an error popup?)
        boolean settingsLoaded = settings.checkAndLoad();

        if (!settingsLoaded) {
            System.exit(-10);
        }

        LoginController loginController = configureController(new LoginController());
        configureController(new ContactsController());
        configureController(new ConversationsController());

        /**
         * --------------------------
         * Event listeners
         * --------------------------
         */
        eventManager.attach(MvcEvent.WINDOW_CLOSING, new SaveOnExitListener());


        /**
         * --------------------------
         * Running the application
         * --------------------------
         */
        loginController.displayLogin();
    }

    /**
     * Configures a controller and injects all the dependencies
     *
     * @param controller Controller object to be configured
     * @return A configured controller object
     */
    private <T extends AbstractController> T configureController(T controller)
    {
        // Configuring the controller
        controller.setEventManager(eventManager);

        if (controller instanceof PropertiesAwareInterface) {
            ((PropertiesAwareInterface) controller).setProperties(settings);
        }

        controller.completeSetup();

        return controller;
    }

    /**
     * The event listener will handle window closing events
     *
     * @version 1.0
     * @link https://github.com/brian978/JustChat
     * @since 2014-06-27
     */
    private class SaveOnExitListener extends AbstractEventListener
    {
        /**
         * The method is called by the event manager when an EventListener class is passed to the trigger() method
         *
         * @param e Event that was triggered
         */
        @Override
        public void onEvent(EventInterface e)
        {
            if (e.getTarget() instanceof Contacts || e.getTarget() instanceof Login) {
                try {
                    settings.store();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
