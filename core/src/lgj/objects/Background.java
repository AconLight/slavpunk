package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background extends GameObject {

    SpriteObject background, background2;
    boolean isMine;
    float posX, posY, scale;
    int width = (int)(2000*9);

    public Background(boolean isMine, String id) {
        super(0, id);
        this.isMine = isMine;

        posX = -1750;
        posY = -1040;
        scale = 8f;

        setPosition(posX, posY);
        spriteInit();
        addActor(background);
        addActor(background2);
    }

    void spriteInit() {
        background = AssetLoader.getAsset("background", index);
        background.scaleBy(scale);
        background2 = AssetLoader.getAsset("background", index);
        background2.scaleBy(scale);
        background2.setPosition(background.getX() + width, background.getY());
    }

    public void act(float delta) {
        super.act(delta);
        background.setPosition(background.getX() - 3, background.getY());
        background2.setPosition(background.getX() + width, background.getY());
        if (background.getX() < -width) {
            background.setPosition(background.getX() + 2*width, background.getY());
            SpriteObject temp = background;
            background = background2;
            background2 = temp;
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }
}
