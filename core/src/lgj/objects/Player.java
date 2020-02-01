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
    boolean isMine, isInMovement;
    float posX, posY, scale, velocity;
    String state;

    public Player(boolean isMine,String id){
        super(0, id);
        this.isMine = isMine;

        posX = 200;
        posY = 260;
        scale = 10;
        velocity = 5;
        isInMovement = false;
        state = "idle";
        animationInit();

        addActor(head);
        addActor(body);
        addActor(arm);
        addActor(hamer);
        addActor(legs);
    }

    void animationInit(){
        SpriteObject animation = new SpriteObject();
        posX = 0;
        posY = 0;
        head = AssetLoader.getAnimation("head");
        head.chooseAnimation("idle_right");
        head.setPosition(posX, posY);
        head.setScale(scale);

        body = AssetLoader.getAnimation("body");
        body.chooseAnimation("idle_right");
        body.setPosition(posX, posY);
        body.setScale(scale);

        arm = AssetLoader.getAnimation("arm");
        arm.chooseAnimation("idle_right");
        arm.setPosition(posX, posY);
        arm.setScale(scale);

        hamer = AssetLoader.getAnimation("hamer");
        hamer.chooseAnimation("idle_right");
        hamer.setPosition(posX, posY);
        hamer.setScale(scale);

        legs = AssetLoader.getAnimation("legs");
        legs.chooseAnimation("idle_right");
        legs.setPosition(posX, posY);
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
    void changeAnimation(String state){

        head.chooseAnimation(head + "_" + state);
        body.chooseAnimation(body + "_" + state);
        body.chooseAnimation(arm + "_" + state);
        body.chooseAnimation(hamer + "_" + state);
        legs.chooseAnimation(legs + "_" + state);

    }

    public void act(float delta) {
        super.act(delta);
        state = "idle";

        if (isMine) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                posY += velocity;
                isInMovement = true;
                //state = "up";
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                posY -= velocity;
                isInMovement = true;
                //state = "down";
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                posX += velocity;
                isInMovement = true;
                state = "right";
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                posX -= velocity;
                isInMovement = true;
                state = "left";
            }
        }


       // isInMovement = false;

        updatePos();

        if (isMine) {
            sendPos();
            changeAnimation(state);
            sendAnimationUpdate();
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

    void sendAnimationUpdate(){
        NetworkManager.networkManager.addEventToSend(new Event(id + " changeAnimation String " + state));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

}
