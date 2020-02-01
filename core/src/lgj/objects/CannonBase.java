package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;

public class CannonBase extends GameObject {

    SpriteObject ship;
    boolean isMine;
    float posX, posY, scale;

    public CannonBase(boolean isMine, String id, int x, int y) {
        super(4, id);
        this.isMine = isMine;

        setPosition(x,y);
        scale = 8;
        spriteInit();

        addActor(ship);
    }

    void spriteInit() {
        ship = AssetLoader.getAsset("cannonBase", index);
        ship.scaleBy(scale);
    }

    public void act(float delta) {
        super.act(delta);
        //updatePos();
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }
}
