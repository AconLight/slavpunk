package com.mygdx.gameObjects;

import boost.GameObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.networking.NetworkApi;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class NetSpawner extends GameObject {

    NetTest test;
    Stage stage;

    public NetSpawner(Stage stage) {
        super(0, "spawner");
        this.stage = stage;
    }

    public void spawn(String id) {
        test = new NetTest(false, id);
        stage.addActor(test);
    }

    public void spawnAsMine() {
        test = new NetTest(true, "player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        stage.addActor(test);
    }
}
