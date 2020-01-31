package com.mygdx.events;

import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class Event {
    public ArrayList<String> data;

    public Event(String data) {
        this.data = new ArrayList<>();
        for (String d: data.split(" ")) {
            this.data.add(d);
        }
    }
}
