package com.justchat.mvc.controller;

import com.acamar.event.Event;
import com.acamar.event.EventManager;
import com.acamar.mvc.controller.AbstractController;

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
        try {
            Method method = this.getClass().getMethod("updateLoginFields", Event.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void updateLoginFields(Event e)
    {

    }

    public void displayLogin()
    {

    }
}
