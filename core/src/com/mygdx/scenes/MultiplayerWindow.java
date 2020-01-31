package com.mygdx.scenes;

import assets.AssetLoader;
import boost.GameObject;
import boost.MyScene;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.gameComponents.Clickable;
import com.mygdx.gameObjects.Test;
import com.mygdx.networking.Address;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import lobby.LobbyObject;

import java.util.ArrayList;

public class MultiplayerWindow extends MyScene {

    GameObject addPlayer, bg, play;

    public LobbyObject lobby;

    public MultiplayerWindow() {
        super();
        bg = AssetLoader.getAsset("howto");
        bg.setScale(4);
        bg.setShader("drunk");
        stage.addActor(bg);
        lobby = new LobbyObject();
        stage.addActor(lobby);
    }

    public void act() {
        super.act();

    }
}
