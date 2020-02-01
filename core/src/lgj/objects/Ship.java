package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;

public class Ship extends GameObject {

    SpriteObject ship;
    boolean isMine;
    float posX, posY, scale;

    public Ship(boolean isMine, String id) {
        super(1, id);
        this.isMine = isMine;

        posX = 200;
        posY = 260;
        scale = 10;
        spriteInit();

        addActor(ship);
    }

    void spriteInit() {
        posX = 0;
        posY = 0;
        ship = AssetLoader.getAsset("ship");
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
}
