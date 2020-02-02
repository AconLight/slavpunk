package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.SpriteObject;
import com.mygdx.networking.NetworkApi;
import java.util.Random;

public class Part extends GameObject {

    SpriteObject part;
    boolean isMine;
    float posX, posY, scale, velocity;
    Vacuum vacuum;

    float gravity = -0.2f;
    float vx, vy;


    public Part(float x, float y, boolean isMine, String id, int seed) {
        super(3, id);
        this.isMine = isMine;
        Random rand = new Random((long)seed);
        vx = rand.nextInt(7) - 3;
        vy = rand.nextInt(12);
        setPosition( x, y);
        posX = x;
        posY = y;
        scale = 6;
        spriteInit();
        velocity = 3;

        addActor(part);
    }

    void spriteInit() {
        part = AssetLoader.getAsset("part", index);
        part.scaleBy(scale);
    }

    public void act(float delta) {
        super.act(delta);


        if (isMine) {
            posX -= velocity;
        }

        if (posX > 800 && posX < 850) {
            if(vacuum == null) {
                vacuum = (Vacuum) GameObjectManager.gameObjects.get("vacuum");
            }
            if(vacuum.isOn) {
                velocity = 0;
                posY += 3;
            } else {
                velocity = 3;
                posY = -200;
            }
            if(posY > 220) {
                vacuum.charge();
                remove();
            }
        }
        if (posX > 1600) {
            vy += gravity;
            posX += vx;
            posY += vy;
            if(posY < -200) {
                posY = -200;
                vx = 0;
            }
        }

        updatePos();

        if(posX < -2000) {
            remove();
        }

        if (isMine) {
            //sendPos();
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }
}
