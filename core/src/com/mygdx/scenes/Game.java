package com.mygdx.scenes;

import boost.MyScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.events.Event;
import com.mygdx.gameObjects.NetSpawner;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import lgj.objects.Enemy;
import lgj.objects.Player;
import lgj.spawner.MyNetSpawner;
import java.util.Random;
import java.util.ArrayList;

public class Game extends MyScene {

    MyNetSpawner netSpawner;

    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static ArrayList<Player> players = new ArrayList<>();

    public static int playersNumb = 0;

    public Game() {
        super();
        netSpawner = new MyNetSpawner(stage);
    }

    public void restart() {
        i = 0;
        netSpawner = new MyNetSpawner(stage);
        enemies = new ArrayList<>();
        players = new ArrayList<>();
        playersNumb = 0;
        waveNumb = 5;
        waveDur = 1000;
        stage.clear();
//        for (Actor a: stage.getActors()) {
//            a.remove();
//        }
        spawn();
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
    Random rand = new Random();

    int waveNumb = 5;
    int waveDur = 1000;
    public void act() {
        super.act();

        if (i%waveDur < waveNumb*15 && i%15 == 0) {
            if(NetworkManager.networkManager.isHost) {
                netSpawner.spawnMinionEveryWhere(i % waveDur);
                if (rand.nextInt(waveNumb) == 0) {
                    waveNumb++;
                    waveDur = waveNumb*200;
                }
            }
        }
        i++;
    }
}
