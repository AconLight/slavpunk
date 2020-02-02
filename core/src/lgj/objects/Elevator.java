package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Elevator extends GameObject {

    SpriteObject elevator;
    boolean isMine;
    float posX, posY, scale;

    public Elevator(boolean isMine, String id) {
        super(6, id);
        this.isMine = isMine;

        posX = 200;
        posY = 260;
        scale = 8;
        spriteInit();

        addActor(elevator);
        elevator.setShader("drunk");
    }

    void spriteInit() {
        posX = 0;
        posY = 0;
        elevator = AssetLoader.getAsset("elevator", index);
        elevator.scaleBy(scale);
    }

    float time = 0;
    public void act(float delta) {
        super.act(delta);
        updatePos();
        time += delta;
        elevator.shader.begin();
        elevator.shader.setUniformf("time", time/3);
        elevator.shader.end();
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }
}
