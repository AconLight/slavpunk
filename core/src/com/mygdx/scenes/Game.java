package com.mygdx.scenes;

import boost.MyScene;
import com.mygdx.events.Event;
import com.mygdx.gameObjects.NetSpawner;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;

public class Game extends MyScene {

    NetSpawner netSpawner;

    public Game() {
        super();
        netSpawner = new NetSpawner(stage);
    }

    int i = 0;

    public void spawn() {
        netSpawner.spawnAsMine();
        NetworkManager.networkManager.addEventToSend(new Event("spawner spawn String player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
    }
    public void act() {
        super.act();
        if (i == 0) {
            spawn();
        }
        i++;
    }
}
