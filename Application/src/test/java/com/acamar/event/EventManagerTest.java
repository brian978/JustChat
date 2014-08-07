package com.acamar.event;

import com.acamar.event.listener.AbstractEventListener;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * JustChat
 *
 * @version 1.0
 * @link https://github.com/brian978/JustChat
 * @since 2014-07-03
 */
public class EventManagerTest
{
    EventManager eventManager = new EventManager();

    @Test
    public void listenerCanBeAttached()
    {
        eventManager.attach("some.event", new AbstractEventListener()
        {
            @Override
            public void onEvent(EventInterface e)
            {
                // Do nothing
            }
        });

        Assert.assertEquals(1, eventManager.getListeners("some.event").size());
    }

    @Test
    public void managerCanTriggerEvents()
    {
        AbstractEventListener listener = new AbstractEventListener()
        {
            @Override
            public void onEvent(EventInterface e)
            {
                HashMap<String, Integer> params = (HashMap<String, Integer>)e.getParams();
                Integer value = params.get("value");
                params.put("value", ++value);
            }
        };

        eventManager.attach("some.event", listener);
        eventManager.attach(Event.class, listener);


        HashMap<Object, Object> params = new HashMap<>();
        params.put("value", 1);

        // Triggering the event multiple times to check that all the methods work properly
        eventManager.trigger(new Event("some.event", this, params));
        eventManager.trigger("some.event", this, params);
        eventManager.trigger("some.event", new Event("some.event", this, params));

        Assert.assertEquals(new Integer(4), new Integer((int) params.get("value")));
    }

    @Test
    public void managerCanTriggerListenersInOrder()
    {
        final ArrayList<String> list = new ArrayList<>();

        AbstractEventListener listener1 = new AbstractEventListener()
        {
            @Override
            public void onEvent(EventInterface e)
            {
                list.add("First listener");
            }
        };

        AbstractEventListener listener2 = new AbstractEventListener()
        {
            @Override
            public void onEvent(EventInterface e)
            {
                list.add("Second listener");
            }
        };

        listener1.setPriority(100);


        eventManager.attach("some.event6", listener2);
        eventManager.attach("some.event6", listener1);
        eventManager.trigger("some.event6", this);


        Assert.assertArrayEquals(new Object[]{"First listener", "Second listener"}, list.toArray());
    }
}
