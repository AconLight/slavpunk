package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;

public class Stock extends GameObject {

    SpriteObject ship;
    boolean isMine;
    float posX, posY, scale;

    public Stock(boolean isMine, String id, int x, int y) {
        super(1, id);
        this.isMine = isMine;

        posX = x;
        posY = y;
        scale = 8;
        spriteInit();

        addActor(ship);
    }

    void spriteInit() {
        posX = 0;
        posY = 0;
        ship = AssetLoader.getAsset("stock", index);
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
