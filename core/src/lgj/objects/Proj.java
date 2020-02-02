package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkManager;
import com.mygdx.scenes.MySceneManager;

import java.util.ArrayList;

public class Proj extends GameObject {

    float vx, vy;
    public static int numb = 0;
    public Boolean isMine;
    public Proj(Boolean isMine, String id, float x, float y, float vx, float vy) {
        super(3, id);
        this.isMine = isMine;
        this.vx = vx;
        this.vy = vy;
        setPosition(x ,y);
        GameObject go = AssetLoader.getAsset("proj", 3);
        go.setScale(2);
        addActor(go);
    }

    public void onHit() {
        remove();
    }

    ArrayList<Enemy> enemToRem = new ArrayList<>();
    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + vx, getY() + vy);
        enemToRem.clear();
        if (isMine) {
            for (Enemy e : MySceneManager.game.enemies) {
                if (e.getX() < getX() && e.getX() + e.scale * 50 > getX() &&
                        e.getY() < getY() && e.getY() + e.scale * 60 > getY()) {
                    enemToRem.add(e);
                    break;
                }
            }
        }

        for (Enemy e: enemToRem) { //only once
            if (isMine) {
                int seed = (int)(e.getX()*100);
                NetworkManager.networkManager.addEventToSend(new Event(e.id + " die int " + seed));
                e.die(seed);
                NetworkManager.networkManager.addEventToSend(new Event(id + " onHit"));
                onHit();
            }
        }

        if (getX() > 30000) {
            remove();
        }



    }
}
