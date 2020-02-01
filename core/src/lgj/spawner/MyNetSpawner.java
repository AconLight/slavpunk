package lgj.spawner;

import boost.GameObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.gameObjects.NetTest;
import com.mygdx.networking.NetworkApi;
import lgj.objects.Player;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class MyNetSpawner extends GameObject {

    Player test;
    Stage stage;

    public MyNetSpawner(Stage stage) {
        super(0, "spawner");
        this.stage = stage;
    }

    public void spawn(String id) {
        test = new Player(false, id);
        stage.addActor(test);
    }

    public void spawnAsMine() {
        test = new Player(true, "player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        stage.addActor(test);
    }
}
