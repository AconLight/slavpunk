package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CannonBase extends GameObject {

    SpriteObject cannonBase, cannon;
    boolean isMine;
    float posX, posY, scale,bx,by;
    String playerUsing = "";
    private float realRotation;
    private float myRotation;
    public ArrayList<Integer> parts;
    public ArrayList<String> playerIds;
    public ArrayList<Progress> bars;

    //AssetLoader.pistol.play();  dzwiek strzelania


    public CannonBase(boolean isMine, String id, int x, int y) {
        super(4, id);
        this.isMine = isMine;
        bx = x;
        by = y;

        parts = new ArrayList<>();
        playerIds = new ArrayList<>();
        bars = new ArrayList<>();

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

    public void setRotation(Float rot) {
        realRotation = rot;
    }

    public void fix(String id) {
        int i = 0;
        for (String address : playerIds) {
            if (address.equals(id)) {
                ((Player) GameObjectManager.gameObjects.get(id)).parts--;
                parts.set(i, parts.get(i) + 1);
                break;
            }
            i++;
        }
        Gdx.app.log("CannonBase", "fix");
        for (int p : parts) {
            Gdx.app.log("CannonBase", "part: " + p);
        }
    }

    float prevSendRotation = 0;

    public void act(float delta) {
        super.act(delta);



        if (playerIds.size() > bars.size()) {

            Color color = ((Player) GameObjectManager.gameObjects.get(playerIds.get(bars.size()))).myCol;

            if(color != null) {
                bars.add(new Progress((int) (bx + 350), (int) (by + 560 + 25 * bars.size()), 200, 20, color));
            }
        }


        String myPlayerId = "player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port;
        if (!playerUsing.equals("") && myPlayerId.equals(playerUsing)) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                if (realRotation <= 38)
                    realRotation += 0.5f;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                if (realRotation >= -69)
                    realRotation -= 0.5f;
            }
            boolean canShoot = true;
            for (Integer p : parts) {
                if (p <= 0) {
                    canShoot = false;
                    break;
                }
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.G) && canShoot) {
                float vx = (float) (50 * Math.cos(myRotation * Math.PI / 180));
                float vy = (float) (50 * Math.sin(myRotation * Math.PI / 180));
                Proj.numb++;
                int n = Proj.numb;
                shoot(vx, vy, "mine", "proj" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port + n);
                NetworkManager.networkManager.addEventToSend(new Event(id + " shoot " + "float " + vx + " float " + vy + " String not String " + "proj" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port + n));
            }
        }

        myRotation = (realRotation + myRotation * 5) / 6;
        cannon.rotateBy(-cannon.getRotation() + myRotation);
        if (!playerUsing.equals("")) {
            if ((prevSendRotation - realRotation) * (prevSendRotation - realRotation) > 1.5) {
                NetworkManager.networkManager.addEventToSend(new Event(id + " setRotation " + "float " + realRotation));
                prevSendRotation = realRotation;
            }
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }

    public void shoot(Float vx, Float vy, String isMine, String id) {
        Gdx.app.log("CannonBase", "vx: " + vx + ", vy: " + vy);
        Boolean myIsMine = false;
        if (isMine.equals("mine")) {
            myIsMine = true;
        }
        getStage().addActor(new Proj(myIsMine, id,
                cannon.getX() + getX(), cannon.getY() + getY(), vx, vy));

        for (int i = 0; i < parts.size(); i++) {
            parts.set(i, parts.get(i) - 1);
        }
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

    @Override
    public void draw(Batch batch, float parentAlfa) {
        super.draw(batch, parentAlfa);
        for (Progress bar : bars) {
            bar.getBar().draw(batch, parentAlfa);
        }
    }
}
