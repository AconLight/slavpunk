package com.mygdx.scenes;

import boost.SceneManager;
import com.mygdx.events.EventHandler;
import com.mygdx.events.EventsHolder;
import com.mygdx.networking.NetworkManager;

public class MySceneManager extends SceneManager {
    public static Intro intro;
    public static Menu menu;
    public static Game game;
    public static MultiplayerWindow multiplayerWindow;
    public static Pause pause;

    public EventHandler eventHandler;
    public EventsHolder eventsHolder;

    @Override
    public void createSceneManager() {
        super.createSceneManager();
        intro = new Intro();
        menu = new Menu();
        multiplayerWindow = new MultiplayerWindow();
        game = new Game();
        pause = new Pause();
        addScene(intro);

        eventsHolder = new EventsHolder();
        eventHandler = new EventHandler(eventsHolder);
        NetworkManager.create(false, eventsHolder, eventHandler);
    }

    int networkDistributionCounter = 0;

    @Override
    public void act() {
        super.act();

        // events handling (from network)
        eventHandler.handleEvents();
        if (networkDistributionCounter > 5) {
            networkDistributionCounter = 0;
            NetworkManager.networkManager.distributeEvents();
        }
        networkDistributionCounter++;
    }
}
