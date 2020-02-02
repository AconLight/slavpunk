package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;
import com.mygdx.scenes.MySceneManager;

public class Ship extends GameObject {

    SpriteObject ship, boosters;
    boolean isMine;
    float posX, posY, scale;
    int health, maxHealth;
    Progress healthBar;

    public void gameOver() {
        MySceneManager.switchToScene(MySceneManager.pause);
    }
    int i = 0;
    public Ship(boolean isMine, String id) {
        super(1, id);
        this.isMine = isMine;

        health = 512;
        maxHealth = health;

        healthBar = new Progress(200, 1750, 1900, 50, Color.RED);

        posX = 200;
        posY = 260;
        scale = 8;
        spriteInit();

        addActor(ship);
        addActor(boosters);
    }

    void spriteInit() {
        posX = 0;
        posY = 0;
        ship = AssetLoader.getAsset("ship", index);
        ship.scaleBy(scale);
        boosters = AssetLoader.getAsset("boosters", index);
        boosters.scaleBy(scale);
        boosters.setShader("drunk");
    }
    float time = 0;
    public void act(float delta) {
        super.act(delta);
        updatePos();
        i++;
        if (i%20 == 0) {
            if (health < maxHealth)
                health++;
        }

        time += delta;
        boosters.shader.begin();
        boosters.shader.setUniformf("time", time/3);
        boosters.shader.end();
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }

    public void dealDamage() {
        health--;
        healthBar.getBar().setValue((float) health / (float) maxHealth);
        if (health <= 0) {
            if (NetworkManager.networkManager.isHost) {
                NetworkManager.networkManager.addEventToSend(new Event("ship gameOver"));
                gameOver();
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        super.draw(batch, parentAlfa);
        healthBar.getBar().draw(batch, parentAlfa);
    }
}
