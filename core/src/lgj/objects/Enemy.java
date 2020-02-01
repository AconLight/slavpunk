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

        posX = 250;
        posY = 250;
        scale = 10;
        velocity = 0;

        colorInit();
        animationInit();
        changeColor();

    }

    void colorInit(){
        colory = new HashMap<>();
        int id = 0;
        pink = new Color(255/255f, 0/255f, 102/255f, 255/255f);
        colory.put(++id,pink);
        green = new Color(102/255f, 255/255f, 102/255f, 255/255f);
        colory.put(++id,green);
        blue = new Color(0/255f, 255/255f, 255/255f,255/255f);
        colory.put(++id,blue);
        yellow = new Color(255/255f, 204/255f, 0/255f,255/255f);
        colory.put(++id,yellow);
    }

    void animationInit(){



        legs_ = AssetLoader.getAnimation("enemy_legs", 2);
        legs_.chooseAnimation(Integer.toString(legs));
        legs_.setScale(scale);
        addActor(legs_);

        body_ = AssetLoader.getAnimation("enemy_body", 2);
        body_.chooseAnimation(Integer.toString(body));
        body_.setScale(scale);
        addActor(body_);

        head_ = AssetLoader.getAnimation("enemy_head", 2);
        head_.chooseAnimation(Integer.toString(head));
        head_.setScale(scale);
        addActor(head_);

        weapon_ = AssetLoader.getAnimation("enemy_weapon", 2);
        weapon_.chooseAnimation(Integer.toString(weapon));
        weapon_.setScale(scale);
        addActor(weapon_);

        renka_ = AssetLoader.getAnimation("enemy_renka", 2);
        renka_.chooseAnimation(Integer.toString(renka));
        renka_.setScale(scale);
        addActor(renka_);

        eye_ = AssetLoader.getAnimation("enemy_eye", 2);
        eye_.chooseAnimation(Integer.toString(eye));
        eye_.setScale(scale);
        addActor(eye_);

    }



    void changeColor(){
        eye_.color = (colory.get(eye_color));
        weapon_.color = (colory.get(weapon_color));
        legs_.color = (colory.get(legs_color));

    }

    public void act(float delta) {
        super.act(delta);


        if (isMine) {
                //posX -= velocity;
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
        // NetworkManager.networkManager.addEventToSend(new Event(id + " updatePos float " + posX + " float " + posY));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
    
}
