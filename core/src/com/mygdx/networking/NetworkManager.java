package com.mygdx.networking;

import com.badlogic.gdx.Gdx;
import com.mygdx.events.Event;
import com.mygdx.events.EventHandler;
import com.mygdx.events.EventParser;
import com.mygdx.events.EventsHolder;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class NetworkManager {
    ArrayList<Address> toAddresses;
    public boolean isHost;
    EventsHolder eventsHolder;
    EventHandler eventHandler;

    public static NetworkManager networkManager;

    public static void create(boolean isHost, EventsHolder eventsHolder, EventHandler eventHandler) {
        networkManager = new NetworkManager(isHost, eventsHolder, eventHandler);
    }

    public NetworkManager(boolean isHost, EventsHolder eventsHolder, EventHandler eventHandler) {
        this.isHost = isHost;
        toAddresses = new ArrayList<>();
        this.eventsHolder = eventsHolder;
        this.eventHandler = eventHandler;
    }

    // when host
    public void addNetAddress(Address address) {
        toAddresses.add(address);
    }


    //when not host
    public void setHostAddress(Address address) {
        toAddresses.clear();
        toAddresses.add(address);
    }

    public void createEventsToHandle(String data) {
        ArrayList<Event> events = EventParser.createEvents(data);
        eventsHolder.addEventsToHandle(events);
        Gdx.app.log("NetworkManager", "isHost: " + isHost);
        if (isHost) {
            for (Event e: events) {
                addEventToSend(e);
            }
        }
    }

    public void addEventToSend(Event event) {
        eventsHolder.addEventToSend(event);
    }

    public void distributeEvents() {
        ArrayList<Event> events = eventsHolder.popEventsToSend();
        if (events.size() > 0) {
            for (Address a : toAddresses) {
                String to = a.port + ":" + a.ip;
                NetworkApi.manager.send(a.ip, a.port, EventParser.createMessage(events, to));
            }
        }
    }
}
