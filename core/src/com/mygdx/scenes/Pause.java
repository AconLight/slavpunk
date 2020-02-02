package com.mygdx.scenes;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.MyScene;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;
import lgj.objects.Player;
import lgj.objects.Ship;

public class Pause extends MyScene {
    GameObject splash;

    float time;

    public Pause() {
        super();
        splash = AssetLoader.getAsset("gameover");
        splash.alfa = 0;
        stage.addActor(splash);
        time = 0;
    }

    public void restart() {
        stage.clear();
        splash = AssetLoader.getAsset("gameover");
        splash.alfa = 0;
        stage.addActor(splash);
        time = 0;
    }

    float k = 0.1f;
    boolean flag = true, flag2 = true;
    public void act() {
        super.act();
        time += Gdx.graphics.getDeltaTime();
        splash.alfa = time/2;
        if (splash.alfa > 1) {
            splash.alfa = 1;
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
                NetworkManager.networkManager.addEventToSend(new Event(MySceneManager.game.players.get(0).id + " restartGame"));
                (MySceneManager.game.players.get(0)).restartGame();
            }
        }
    }
}
