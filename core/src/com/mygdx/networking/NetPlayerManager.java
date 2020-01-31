package com.mygdx.networking;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class NetPlayerManager {

    public static NetPlayerManager netPlayerManager;

    public static void create() {
        netPlayerManager = new NetPlayerManager();
    }

    public void sendEventToHost(String data) {

    }

    private void handleHostEvent(String data) {

    }
}
