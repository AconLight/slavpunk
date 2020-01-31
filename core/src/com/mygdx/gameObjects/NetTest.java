package com.mygdx.gameObjects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class NetTest extends GameObject {

    SpriteObject jade;
    boolean isMine;

    float posX, posY;

    public NetTest(boolean isMine, String id) {
        super(0, id);
        this.isMine = isMine;
        jade = AssetLoader.getAnimation("jade", 20);
        jade.chooseAnimation("idle");
        jade.setScale(10);
        // jade.setShader("invert");
        posX = 200;
        posY = 260;
        jade.setPosition(200, 260);
        addActor(jade);
    }

    public void updatePos() {
        float x = jade.getX();
        float y = jade.getY();
        jade.setPosition((posX + x)/2, (posY + y)/2);
    }

    public void sendPos() {
        NetworkManager.networkManager.addEventToSend(new Event(id + " updatePos float " + posX + " float " + posY));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

    public void act(float delta) {
        super.act(delta);

        if (isMine) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                posY += 5;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                posY -= 5;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                posX += 5;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                posX -= 5;
            }
        }

        updatePos();

        if (isMine) {
            sendPos();
        }



    }
}
