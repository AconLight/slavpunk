package com.mygdx.scenes;

import assets.AssetLoader;
import boost.GameObject;
import boost.MyScene;
import boost.SceneManager;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.mygdx.gameComponents.Clickable;
import com.mygdx.gameObjects.Test;
import lobby.AcceptObject;

public class Menu extends MyScene {
    GameObject bg, play, quit, test, accept;

    float time, startGameTime;
    public Menu() {
        super();
        bg = AssetLoader.getAsset("howto");
        bg.setScale(4);
        bg.setShader("drunk");
        quit = AssetLoader.getAsset("quit");
        quit.addComponent(new Clickable(quit, (SpriteObject) quit, () -> Gdx.app.exit()));
        quit.setPosition(1920/2 - 320, 1280/2 - 128 - 128 - 64);
        play = AssetLoader.getAsset("play");
        play.addComponent(new Clickable(play, (SpriteObject) play, () -> startMultiplayerWindow()));
        play.setPosition(1920/2 - 320, 1280/2 - 128 + 128 + 64);
        test = new Test();
        test.setPosition(100, 50);
        bg.alfa = 0;
        play.alfa = 0;
        quit.alfa = 0;
        stage.addActor(bg);
        stage.addActor(play);
        stage.addActor(quit);
        // stage.addActor(test);
        time = 0;
        startGameTime = -1f;

        accept = new AcceptObject();
        stage.addActor(accept);
    }

    public void startMultiplayerWindow() {
        SceneManager.switchToScene(MySceneManager.multiplayerWindow);
    }

    float k = 0.1f;
    public void act() {
        super.act();
        time += Gdx.graphics.getDeltaTime();
        bg.shader.begin();
        bg.shader.setUniformf("time", time/10);
        bg.shader.end();
        if (time/k < Math.PI/2) {
            float a = (float) Math.sin(time/k);
            bg.alfa = a*a*a*a*a*a;
            play.alfa = a*a*a*a*a*a;
            quit.alfa = a*a*a*a*a*a;
        } else {
            bg.alfa = 1f;
            play.alfa = 1f;
            quit.alfa = 1f;
        }
    }
}
