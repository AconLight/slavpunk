package com.mygdx.scenes;

import assets.AssetLoader;
import boost.GameObject;
import boost.MyScene;
import com.badlogic.gdx.Gdx;

public class Intro extends MyScene {
    GameObject splash;

    float time;

    public Intro() {
        super();
        splash = AssetLoader.getAsset("redartedSplash");
        splash.alfa = 0;
        stage.addActor(splash);
        time = 0;
    }

    float k = 4f;
    boolean flag = true, flag2 = true;
    public void act() {
        super.act();
        time += Gdx.graphics.getDeltaTime();
        float a = (float) Math.sin(time/k);
        splash.alfa = a*a*a*a*a*a;
        if (time/k > Math.PI*0.7f && flag) {
            MySceneManager.addScene(MySceneManager.menu);
            MySceneManager.menu.time = 0;
            flag = false;
        }
        if (time/k > Math.PI && flag2) {
            MySceneManager.removeScene(this);
            MySceneManager.switchInputToScene(MySceneManager.menu);
            flag2 = false;
        }
    }
}
