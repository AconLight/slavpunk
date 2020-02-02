package com.mygdx.scenes;

import boost.MyScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.events.Event;
import com.mygdx.gameObjects.NetSpawner;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import lgj.objects.Enemy;
import lgj.spawner.MyNetSpawner;

import java.util.ArrayList;

public class Game extends MyScene {

    MyNetSpawner netSpawner;

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static int playersNumb = 0;

    public Game() {
        super();
        netSpawner = new MyNetSpawner(stage);
    }

    public int i = 0;

    public void spawn() {
        Gdx.app.log("MyNetSpawner111", "spawn vacuum");
        netSpawner.spawnAsMine();
        Gdx.app.log("MyNetSpawner222", "spawn vacuum");
        NetworkManager.networkManager.addEventToSend(new Event("spawner spawn String player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port));
        if(NetworkManager.networkManager.isHost)  {
            netSpawner.spawnCannonBaseAsMine("cannonBase1", "bottom");
            netSpawner.spawnCannonBaseAsMine("cannonBase2", "middle");
            netSpawner.spawnCannonBaseAsMine("cannonBase3", "top");
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase1 String bottom"));
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase2 String middle"));
            NetworkManager.networkManager.addEventToSend(new Event("spawner spawnCannonBase String cannonBase3 String top"));
        }
    }
    public void act() {
        super.act();

        if (i%4000 < 15*5 && i%5 == 0) {
            if(NetworkManager.networkManager.isHost) {
                netSpawner.spawnMinionEveryWhere(i % 4000 / 5);
            }
        }
        i++;
    }
}
