package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class CannonBase extends GameObject {

    SpriteObject cannonBase, cannon;
    boolean isMine;
    float posX, posY, scale;
    String playerUsing = "";

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

    public void act(float delta) {
        super.act(delta);
        if (!playerUsing.equals("")) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if(cannon.getRotation() <= 38)
                cannon.rotateBy(0.5f);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if(cannon.getRotation() >= -69) cannon.rotateBy(-0.5f);
            }
        }
        //updatePos();
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
