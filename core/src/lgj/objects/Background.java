package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;

public class Background extends GameObject {

    SpriteObject background;
    boolean isMine;
    float posX, posY, scale;

    public Background(boolean isMine, String id) {
        super(0, id);
        this.isMine = isMine;

        posX = -1750;
        posY = -1000;
        scale = 8;
        spriteInit();

        addActor(background);
    }

    void spriteInit() {
        background = AssetLoader.getAsset("background", index);
        background.scaleBy(scale);
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
