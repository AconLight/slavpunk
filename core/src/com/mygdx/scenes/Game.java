package com.mygdx.scenes;

import boost.MyScene;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.events.Event;
import com.mygdx.gameObjects.NetSpawner;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import lgj.spawner.MyNetSpawner;

public class Game extends MyScene {

    MyNetSpawner netSpawner;

    public Game() {
        super();
        netSpawner = new MyNetSpawner(stage);
        ((OrthographicCamera)stage.getCamera()).zoom = 4;
    }

    int i = 0;

    public void spawn() {
        netSpawner.spawnAsMine();
        netSpawner.spawnCannonBaseAsMine("cannonBase1", "bottom");
        netSpawner.spawnCannonBaseAsMine("cannonBase2", "middle");
        netSpawner.spawnCannonBaseAsMine("cannonBase3", "top");
        NetworkManager.networkManager.addEventToSend(new Event("spawner spawn String player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
        if(NetworkManager.networkManager.isHost)  {
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase1 String bottom" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase2 String middle" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase3 String top" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
        }
    }
    public void act() {
        super.act();
        if (i == 0) {
            spawn();
        }

        if (i%4000 < 15*5 && i%5 == 0) {
            if(NetworkManager.networkManager.isHost) {
                netSpawner.spawnMinionEveryWhere(i % 4000 / 5);
            }
        }
        i++;
    }
}
