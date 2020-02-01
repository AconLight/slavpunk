package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;


public class Player extends GameObject {

    SpriteObject head, body, arm, legs, hamer;
    boolean isMine;
    float posX, posY, scale, velocity;
    String state, isRight;
    String isStrike = "not";

    public Player(boolean isMine,String id){
        super(2, id);
        this.isMine = isMine;

        posX = 200;
        posY = 260;
        scale = 4;
        velocity = 5;
        isRight = "right";
        state = "idle";
        animationInit();

        addActor(hamer);
        addActor(head);
        addActor(body);
        addActor(arm);
        addActor(legs);
    }

    void animationInit(){
        posX = 0;
        posY = 485;
        head = AssetLoader.getAnimation("head", index);
        head.animations.get("head_right").setFrameDuration(10f);
        head.animations.get("head_left").setFrameDuration(10f);
        head.animations.get("idle_right").setFrameDuration(10f);
        head.animations.get("idle_left").setFrameDuration(10f);
        head.chooseAnimation("idle_right");
        head.setScale(scale);

        body = AssetLoader.getAnimation("body", index);
        body.chooseAnimation("idle_right");
        body.setScale(scale);

        arm = AssetLoader.getAnimation("arm", index);
        arm.animations.get("idle_right").setFrameDuration(40f);
        arm.animations.get("idle_left").setFrameDuration(40f);
        arm.animations.get("arm_right").setFrameDuration(2f);
        arm.animations.get("arm_left").setFrameDuration(2f);
        arm.chooseAnimation("idle_right");
        arm.setScale(scale);

        hamer = AssetLoader.getAnimation("hamer", index);
        hamer.chooseAnimation("idle_right");
        hamer.setScale(scale);

        legs = AssetLoader.getAnimation("legs", index);
        legs.chooseAnimation("idle_right");
        legs.setScale(scale);
    }

    /**
     * zeby to dzialao, to nazwy folderków muszą być w konwencji:
     * arm_right -> bieganie w prawo ręka bez młotka;
     * idle_left -> stanie w lewo
     * arm - zwykla reka
     * hamer - reka z młotkiem
     * dupa - na tinderze
     * @param state
     */
    public void changeAnimation(String state, String isRight, String isStrike){

        switch (state){             //if w switchu nice mmmmm
            case "idle":{

                    head.chooseAnimation( "idle_" + isRight);
                    arm.chooseAnimation("idle_" + isRight);
                    hamer.chooseAnimation("idle_" + isRight);
                    body.chooseAnimation("idle_" + isRight);
                    legs.chooseAnimation("idle_" + isRight);
                    break;
            }
            default:{
                head.chooseAnimation("head_" + state);
                arm.chooseAnimation("arm_" + state);
                hamer.chooseAnimation("hamer_" + state);
                body.chooseAnimation("body_" + state);
                legs.chooseAnimation("legs_" + state);
            }
        }

        if (isStrike.equals("isStrike")) {
            startStriking(isRight);
        } else {
            stopStriking(isRight);
        }


        Gdx.app.log("posy", Float.toString(posY));
    }

    public void startStriking(String isRight) {
        hamer.chooseAnimation("strike_" + isRight);
    }

    public void stopStriking(String isRight) {
        hamer.chooseAnimation("idle_" + isRight);
    }

    private String prevStrike = "", prevState = "", prevRight = "";

    public void act(float delta) {
        super.act(delta);

        prevRight = isRight;
        prevState = state;
        prevStrike = isStrike;

        if (isMine) {

            if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                isStrike = "isStrike";
            } else {
                isStrike = "not";
            }

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                posY += velocity;
                //state = "up";
            } else
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                posY -= velocity;
                //state = "down";
            } else
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                posX += velocity;
                isRight = "right";
                state = "right";
            } else
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                posX -= velocity;
                isRight = "left";
                state = "left";
            } else {
                state = "idle";
            }

        }

       // isInMovement = false;

        updatePos();

        if (isMine) {
            sendPos();
            changeAnimation(state, isRight, isStrike);
            if (!prevStrike.equals(isStrike) || !prevRight.equals(isRight) || !prevState.equals(state))
                sendAnimationUpdate(isStrike);
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x)/2, (posY + y)/2);
    }

    private float lastSendX = posX;
    private float lastSendY = posY;

    public void sendPos() {
        float dx = posX - lastSendX;
        float dy = posY - lastSendY;
        if (dy*dy + dx*dx > 100) {
            lastSendX = posX;
            lastSendY = posY;
            NetworkManager.networkManager.addEventToSend(new Event(id + " updatePos float " + posX + " float " + posY));
        }
    }

    void sendAnimationUpdate(String isStrike){
        Gdx.app.log("Player", "sendAnimationUpdate");
        NetworkManager.networkManager.addEventToSend(new Event(id + " changeAnimation String " + state + " String " + isRight + " String " + isStrike));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

}
