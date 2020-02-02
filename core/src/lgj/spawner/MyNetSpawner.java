package lgj.spawner;

import boost.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import com.mygdx.scenes.MySceneManager;
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
    Background background;

    public MyNetSpawner(Stage stage) {
        super(0, "spawner");
        this.stage = stage;
    }

    public void spawn(String id) {
        test = new Player(stage, false, id);
        MySceneManager.game.playersNumb++;
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

    Random rand = new Random();
    int enemyNumb = 0;

    public void spawnMinionEveryWhere(int a) {
        int head = rand.nextInt(3) + 1;
        int renka = rand.nextInt(3) + 1;
        int body = rand.nextInt(3) + 1;
        int weapon = rand.nextInt(3) + 1;
        int weapon_color = rand.nextInt(4) + 1;
        int eye = rand.nextInt(3) + 1;
        int eye_color = rand.nextInt(4) + 1;
        int legs = rand.nextInt(3) + 1;
        int legs_color = rand.nextInt(4) + 1;



        float scale = (float)(4 + rand.nextFloat()*12*(1/( 1 + Math.pow(Math.E,(-1*enemyNumb/200f)))));
        int x = (int)(8000 + a * 25 + rand.nextInt(10));
        String id = "enemy" + enemyNumb + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port;
        spawnNextWave(id, x,
                head, renka, body, weapon, weapon_color, eye, eye_color, legs, legs_color, scale
        );
        NetworkManager.networkManager.addEventToSend(new Event("spawner spawnNextWave String " + id + " int " + x + " int " +
                head + " int " + renka + " int " + body + " int " + weapon + " int " + weapon_color + " int " + eye + " int " + eye_color + " int " + legs + " int " + legs_color + " float " + scale
        ));
    }

    public void spawnNextWave(String id, Integer x,  Integer head, Integer renka, Integer body, Integer weapon, Integer weapon_color, Integer eye, Integer eye_color, Integer legs, Integer legs_color, Float scale) {
        enemyNumb++;
        Enemy e = new Enemy(stage, x, -200, true, id,
                head, renka, body, weapon, weapon_color, eye, eye_color, legs, legs_color, scale
        );
        MySceneManager.game.enemies.add(e);
        stage.addActor(e);
    }

    public void spawnAsMine() {
        Gdx.app.log("MyNetSpawner", "spawn vacuum");
        vacuum = new Vacuum(true, "vacuum");
        test = new Player(stage,true, "player" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        MySceneManager.game.playersNumb++;
        ship = new Ship(true, "ship" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        elevator = new Elevator(true, "elevator" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        pipes = new Pipes(true, "pipes" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);
        background = new Background(true, "background" + NetworkApi.manager.myAddress.ip + NetworkApi.manager.myAddress.port);

        stage.addActor(vacuum);
        stage.addActor(test);
        stage.addActor(ship);
        stage.addActor(elevator);
        stage.addActor(pipes);
        stage.addActor(background);
    }
}
