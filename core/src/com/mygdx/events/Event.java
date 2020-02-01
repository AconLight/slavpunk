package com.mygdx.events;

import com.mygdx.networking.NetworkApi;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class Event {
    public ArrayList<String> data;
    public String from;
    public Event(String from, String data) {
        this.data = new ArrayList<>();
        this.from = from;
        for (String d: data.split(" ")) {
            this.data.add(d);
        }
    }
    public Event(String data) {
        this.from = NetworkApi.manager.myAddress.ip + ":" + NetworkApi.manager.myAddress.port;
        this.data = new ArrayList<>();
        this.from = from;
        for (String d: data.split(" ")) {
            this.data.add(d);
        }
    }
}
