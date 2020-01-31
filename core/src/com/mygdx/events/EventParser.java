package com.mygdx.events;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class EventParser {

    public static ArrayList<Event> createEvents(String data) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (String eventData: data.split("nextEvent")) {
            events.add(new Event(eventData));
        }
        return events;
    }

    public static String createMessage(ArrayList<Event> events) {
        String data = "";
        for (Event e: events) {
            data += parseEvent(e);
            data += "nextEvent";
        }
        return data.substring(0, data.length()-9);
    }

    private static String parseEvent(Event event) {
        String data = "";
        for (String s: event.data) {
            data += s + " ";
        }
        return data.substring(0, data.length()-1);
    }
}
