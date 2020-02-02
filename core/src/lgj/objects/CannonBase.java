package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;

public class CannonBase extends GameObject {

    SpriteObject cannonBase, cannon;
    boolean isMine;
    float posX, posY, scale;
    String playerUsing = "";
    private float realRotation;
    private float myRotation;

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

    public void use(String playerId) {
        if (playerUsing.equals("")) {
            playerUsing = playerId;
            Gdx.app.log("using", playerId);
        }
    }

    public void leave(String playerId) {
        if (playerUsing.equals(playerId)) {
            playerUsing = "";
            Gdx.app.log("leaving", playerId);
        }
    }
}
