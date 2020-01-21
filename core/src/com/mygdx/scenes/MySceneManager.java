package com.mygdx.scenes;

import boost.SceneManager;

public class MySceneManager extends SceneManager {
    public static Intro intro;
    public static Menu menu;
    public static Game game;
    public static Pause pause;

    @Override
    public void createSceneManager() {
        super.createSceneManager();
        intro = new Intro();
        menu = new Menu();
        game = new Game();
        pause = new Pause();
        addScene(intro);
    }
}
