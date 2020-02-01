package lgj.spawner;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.gameObjects.NetTest;
import com.mygdx.networking.NetworkApi;
import lgj.objects.Elevator;
import lgj.objects.Player;
import lgj.objects.Ship;
import lgj.objects.Vacuum;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class MyNetSpawner extends GameObject {

    Player test;
    Ship ship;
    Elevator elevator;
    Vacuum vacuum;
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
        ship = new Ship(true, "ship" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        elevator = new Elevator(true, "elevator" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        vacuum = new Vacuum(true, "vacuum" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);

        stage.addActor(test);
        stage.addActor(ship);
        stage.addActor(elevator);
        stage.addActor(vacuum);
    }
}
