package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;

public class CannonBase extends GameObject {

    SpriteObject cannonBase, cannon;
    boolean isMine;
    float posX, posY, scale;
    String playerUsing = "";
    private float realRotation;
    private float myRotation;

    //AssetLoader.pistol.play();  dzwiek strzelania


    public CannonBase(boolean isMine, String id, int x, int y) {
        super(4, id);
        this.isMine = isMine;

        setPosition(x, y);
        scale = 8;
        spriteInit();

        addActor(cannonBase);
        addActor(cannon);
    }

    void spriteInit() {
        cannonBase = AssetLoader.getAsset("cannonBase", index);
        cannonBase.scaleBy(scale);
        cannon = AssetLoader.getAsset("cannon", index);
        cannon.scaleBy(scale);
        cannon.setPosition(400, 400);
        cannon.setOrigin(50, 50);
    }

    public void setRotation(Float rot) {
        realRotation = rot;
    }

    float prevSendRotation = 0;
    public void act(float delta) {
        super.act(delta);
        if (!playerUsing.equals("")) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(realRotation <= 38)
                    realRotation += 0.5f;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if(realRotation >= -69)
                    realRotation -= 0.5f;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
                float vx = (float)(50*Math.cos(myRotation*Math.PI/180));
                float vy = (float)(50*Math.sin(myRotation*Math.PI/180));
                shoot(vx, vy, "mine");
                NetworkManager.networkManager.addEventToSend(new Event(id + " shoot " + "float " + vx + " float " + vy + " String not"));
            }
        }

        myRotation = (realRotation*9 + myRotation) / 10;
        cannon.rotateBy(-cannon.getRotation() + myRotation);
        if (!playerUsing.equals("")) {
            if ((prevSendRotation - realRotation) * (prevSendRotation - realRotation) > 1) {
                NetworkManager.networkManager.addEventToSend(new Event(id + " setRotation " + "float " + realRotation));
                prevSendRotation = realRotation;
            }
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }

    public void shoot(Float vx, Float vy, String isMine) {
        Gdx.app.log("CannonBase", "vx: " + vx + ", vy: " + vy);
        Boolean myIsMine = false;
        if (isMine.equals("mine")) {
            myIsMine = true;
        }
        getStage().addActor(new Proj(myIsMine, "proj" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port,
                cannon.getX() + getX(), cannon.getY() + getY(), vx, vy));
    }

    public void use(String playerId) {
        if (playerUsing.equals("")) {
            playerUsing = playerId;
            Gdx.app.log("using", playerId);
            AssetLoader.cannonStart.play();
        }
    }

    public void leave(String playerId) {
        if (playerUsing.equals(playerId)) {
            playerUsing = "";
            Gdx.app.log("leaving", playerId);
        }
    }
}
