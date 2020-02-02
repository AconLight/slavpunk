package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.SpriteObject;
import com.mygdx.networking.NetworkApi;

public class Part extends GameObject {

    SpriteObject part;
    boolean isMine;
    float posX, posY, scale, velocity;
    Vacuum vacuum;

    public Part(boolean isMine, String id) {
        super(3, id);
        this.isMine = isMine;

        setPosition( 1700, 0);
        posX = 1700;
        posY = 0;
        scale = 8;
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
                vacuum = (Vacuum) GameObjectManager.gameObjects.get("vacuum" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
            }
            if(vacuum.isOn) {
                velocity = 0;
                posY += 3;
            } else {
                velocity = 3;
                posY = 0;
            }
        }
        if(posY > 220) {
            // TODO dodać parta playerowi
            // TODO usunác parta networkingowo
            remove();
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
