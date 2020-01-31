package com.mygdx.events;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class EventsHolder {
    public ArrayList<Event> toSendEvents;
    public ArrayList<Event> toHandleEvents;

    private static int maxEventInMessage = 10;

    public EventsHolder() {
        toSendEvents = new ArrayList<>();
        toHandleEvents = new ArrayList<>();
    }

    public void addEventsToHandle(ArrayList<Event> events) {
        toHandleEvents.addAll(events);
    }

    public void addEventToSend(Event event) {
        toSendEvents.add(event);
    }

    public ArrayList<Event> popEventsToSend() {
        ArrayList<Event> events = new ArrayList<>();
        int i = 0;
        for (Event e: toSendEvents) {
            if (i >= maxEventInMessage) {
                break;
            }
            events.add(e);
            i++;
        }
        toSendEvents.removeAll(events);
        return events;
    }

    public ArrayList<Event> popEventsToHandle() {
        ArrayList<Event> events = new ArrayList<>();
        events.addAll(toHandleEvents);
        toHandleEvents.clear();
        return events;
    }


}
