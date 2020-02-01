package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;

import java.util.HashMap;
import java.util.Map;

public class Enemy extends GameObject {

    SpriteObject head_, renka_, body_, weapon_, eye_, legs_;
    boolean isMine;
    float posX, posY, scale, velocity;
    int head, renka, body, weapon, weapon_color, eye, eye_color, legs, legs_color;
    Color pink, green, blue, yellow;
    Map<Integer,Color> colory;

    public Enemy(boolean isMine,String id, int head, int renka, int body, int weapon, int weapon_color, int eye, int eye_color, int legs, int legs_color){
        super(0, id);
        this.isMine = isMine;
        this.head = head;
        this.renka = renka;
        this.body = body;
        this.weapon = weapon;
        this.weapon_color = weapon_color;
        this.eye = eye;
        this.eye_color = eye_color;
        this.legs = legs;
        this.legs_color = legs_color;

        colorInit();

        posX = 800;
        posY = 260;
        scale = 10;
        velocity = 5;

        animationInit();
        changeColor();

    }

    void colorInit(){
        colory = new HashMap<>();
        int id = 0;
        pink = new Color(255, 0, 102, 255);
        colory.put(id++,pink);
        green = new Color(102, 255, 102, 255);
        colory.put(id++,green);
        blue = new Color(0, 255, 255,255);
        colory.put(id++,blue);
        yellow = new Color(255, 204, 0,255);
        colory.put(id++,yellow);
    }

    void animationInit(){
        posX = 0;
        posY = 0;

        head_ = AssetLoader.getAnimation("head" + head);
        head_.setPosition(posX, posY);
        head_.setScale(scale);
        addActor(head_);

        body_ = AssetLoader.getAnimation("body" + body);
        body_.setPosition(posX, posY);
        body_.setScale(scale);
        addActor(body_);

        renka_ = AssetLoader.getAnimation("renka" + renka);
        renka_.setPosition(posX, posY);
        renka_.setScale(scale);
        addActor(renka_);

        weapon_ = AssetLoader.getAnimation("weapon" + weapon);
        weapon_.setPosition(posX, posY);
        weapon_.setScale(scale);
        addActor(weapon_);

        eye_ = AssetLoader.getAnimation("eye" + eye);
        eye_.setPosition(posX, posY);
        eye_.setScale(scale);
        addActor(eye_);

        legs_ = AssetLoader.getAnimation("legs" + legs);
        legs_.setPosition(posX, posY);
        legs_.setScale(scale);
        addActor(legs_);

    }



    void changeColor(){

        eye_.setColor(colory.get(eye_color));
        weapon_.setColor(colory.get(weapon_color));
        legs_.setColor(colory.get(legs_color));

    }

    public void act(float delta) {
        super.act(delta);


        if (isMine) {
                posX -= velocity;
        }

        updatePos();

        if (isMine) {
            sendPos();
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x)/2, (posY + y)/2);
    }

    public void sendPos() {
        NetworkManager.networkManager.addEventToSend(new Event(id + " updatePos float " + posX + " float " + posY));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

}
