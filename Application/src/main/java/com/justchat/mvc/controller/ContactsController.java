package com.justchat.mvc.controller;

import com.acamar.event.EventManager;
import com.acamar.mvc.controller.AbstractController;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-06-22
 */
public class ContactsController extends AbstractController
{
    /**
     * Creates the object and attaches the required events
     *
     * @param eventManager Event manager object
     */
    public ContactsController(EventManager eventManager)
    {
        this.eventManager = eventManager;
    }

    /**
     * The method will be called after all the dependencies have been injected in the controller
     */
    @Override
    public void completeSetup()
    {

    }
}
