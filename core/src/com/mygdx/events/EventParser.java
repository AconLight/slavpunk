package com.mygdx.events;

import com.badlogic.gdx.Gdx;
import com.mygdx.networking.NetworkApi;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class EventParser {

    public static ArrayList<Event> createEvents(String data) {
        ArrayList<Event> events = new ArrayList<Event>();
        for (String eventData: data.split("nextEvent")) {
            String[] msgs = eventData.split("FROM_SPLITER");
            String myAdr = NetworkApi.manager.myAddress.ip + ":" + NetworkApi.manager.myAddress.port;
            if (!myAdr.equals(msgs[0]))
                events.add(new Event(msgs[0], msgs[1]));
        }
        return events;
    }

    public static String createMessage(ArrayList<Event> events, String to) {
        String data = "";
        for (Event e: events) {
            Gdx.app.log("EventParser", "to: " + to + ", from: " + e.from);
            if (to.equals(e.from)) {
                // continue; //niech wysyła szmaciura, obsłużymy przy odebraniu
            }
            data += e.from;
            data += "FROM_SPLITER";
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
