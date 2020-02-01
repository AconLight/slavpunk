package lgj.spawner;

import boost.GameObject;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import lgj.objects.*;

import java.util.Random;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class MyNetSpawner extends GameObject {

    Player test;
    Ship ship;
    Elevator elevator;
    Vacuum vacuum;
    Pipes pipes;
    Stage stage;
    CannonBase cannonBase1, cannonBase2, cannonBase3;

    public MyNetSpawner(Stage stage) {
        super(0, "spawner");
        this.stage = stage;
    }

    public void spawn(String id) {
        test = new Player(stage, false, id);
        stage.addActor(test);
    }

    public void spawnCannonBase(String id, String position) {
        switch (position) {
            case "bottom":
                cannonBase1 = new CannonBase(true, id, 1630 - 310, 440 - 310);
                stage.addActor(cannonBase1);
                break;
            case "middle":
                cannonBase2 = new CannonBase(true, id, 1710 - 310, 780 - 310);
                stage.addActor(cannonBase2);
                break;
            case "top":
                cannonBase3 = new CannonBase(true, id, 1790 - 310, 1090 - 310);
                stage.addActor(cannonBase3);
                break;
        }
    }

    public void spawnCannonBaseAsMine(String id, String position) {
        switch (position) {
            case "bottom":
                cannonBase1 = new CannonBase(false, id, 1630 - 310, 440 - 310);
                stage.addActor(cannonBase1);
                break;
            case "middle":
                cannonBase2 = new CannonBase(false, id, 1710 - 310, 780 - 310);
                stage.addActor(cannonBase2);
                break;
            case "top":
                cannonBase3 = new CannonBase(false, id, 1790 - 310, 1090 - 310);
                stage.addActor(cannonBase3);
                break;
        }
    }

    public void spawnAsMine() {
        test = new Player(stage,true, "player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        ship = new Ship(true, "ship" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        elevator = new Elevator(true, "elevator" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        vacuum = new Vacuum(true, "vacuum" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        pipes = new Pipes(true, "pipes" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);

        if(NetworkManager.networkManager.isHost) {

        }

        stage.addActor(test);
        stage.addActor(ship);
        stage.addActor(elevator);
        stage.addActor(vacuum);
        stage.addActor(pipes);

        Random rand = new Random();
        stage.addActor(new Enemy(true, "ddd" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port,
                rand.nextInt(3)+1,
                rand.nextInt(3)+1,
                rand.nextInt(3)+1,
                rand.nextInt(3)+1,
                rand.nextInt(4)+1,
                rand.nextInt(3)+1,
                rand.nextInt(4)+1,
                rand.nextInt(3)+1,
                rand.nextInt(4)+1
        ));
    }
}
