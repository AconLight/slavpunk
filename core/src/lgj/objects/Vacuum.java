package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.GameObjectRenderer;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class Vacuum extends GameObject {

    SpriteObject ship;
    boolean isMine;
    float posX, posY, scale;
    boolean isOn = false;
    public ArrayList<Integer> parts;
    public ArrayList<String> playerIds;


    public Vacuum(boolean isMine, String id) {
        super(6, id);
        this.isMine = isMine;
        parts = new ArrayList<>();
        playerIds = new ArrayList<>();
        posX = 200;
        posY = 260;
        scale = 8;
        spriteInit();

        addActor(ship);
    }

    public void pickPart(String id) {
        for (int i = 0; i < playerIds.size(); i++) {
            if (id.equals(playerIds.get(i))) {
                if (parts.get(i) > 0) {
                    parts.set(i, parts.get(i)-1);
                    ((Player)GameObjectManager.gameObjects.get(id)).addParts();
                    Gdx.app.log("Vacuum picked", "picked");
                }
            }
        }
    }

    public void charge() {
        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, parts.get(i)+1);
        }
    }

    void spriteInit() {
        posX = 0;
        posY = 0;
        ship = AssetLoader.getAsset("vacuum", index);
        ship.scaleBy(scale);
    }

    public void act(float delta) {
        super.act(delta);
        updatePos();
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }



    public void setOn() {
        isOn = true;
    }

    public void setOff() {
        isOn = false;
    }
}
