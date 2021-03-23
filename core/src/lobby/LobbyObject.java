package lobby;

import assets.AssetLoader;
import boost.GameComponent;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.mygdx.events.*;
import com.mygdx.events.Event;
import com.mygdx.gameComponents.Clickable;
import com.mygdx.networking.Address;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import com.mygdx.scenes.MySceneManager;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class LobbyObject extends GameObject {

    public ArrayList<PlayerLobbyIcon> players;
    public GameObject addPlayer, play;
    Address host;

    public LobbyObject() {
        super(0, "lobby");
        players = new ArrayList<>();
        addPlayer = AssetLoader.getAsset("add_player");
        addPlayer.setPosition(400, 200);
        addPlayer.addComponent(new Clickable(addPlayer, (SpriteObject) addPlayer, () -> sendInvitation()));
        addActor(addPlayer);

        play = AssetLoader.getAsset("play");
        play.addComponent(new Clickable(play, (SpriteObject) play, () -> startGameClick()));
        play.setPosition(1300, 1280/2 - 128 + 128 + 64);
        addActor(play);
        AssetLoader.soundtrack.setLooping(true);
        AssetLoader.soundtrack.play();
    }

    public void startGameClick() {
        startGame();
        sendStartGame();
    }

    public void startGame() {
        MySceneManager.switchToScene(MySceneManager.game);
        MySceneManager.game.i = 0;
        MySceneManager.game.spawn();
    }

    public void sendStartGame() {
        NetworkManager.networkManager.addEventToSend(new Event("lobby startGame"));
    }

    public void sendInvitation() {
        Gdx.app.log("LobbyObject", "addPlayer");
        String address = "";
        try {
            address = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] address2 = address.split(":");
        String ip = address2[0];
        String port = address2[1];
        Address address3 = new Address(ip, Integer.parseInt(port));
        NetworkManager.networkManager.addNetAddress(address3);
        NetworkManager.networkManager.addEventToSend(new Event("lobby addNewPlayer String " + "25.91.95.188" + " int " + NetworkApi.manager.myAddress.port));
        addNewPlayer(NetworkApi.manager.myAddress.ip, NetworkApi.manager.myAddress.port);
        NetworkManager.networkManager.addEventToSend(new Event("accept showAccept String " + "25.91.95.188" + " int " + NetworkApi.manager.myAddress.port + " int " + players.size()));
    }

    public void addNewPlayer(String hostIp, Integer hostPort) {
        addNewPlayer(hostIp, hostPort, "true");
    }

    public void addNewPlayer(String hostIp, Integer hostPort, String isAwaiting) {
        boolean isA = false;
        if (isAwaiting.equals("true")) {
            isA = true;
        }
        PlayerLobbyIcon p = new PlayerLobbyIcon(isA, players.size());
        this.players.add(p);
        addActor(p);

        host = new Address(hostIp, hostPort);
    }

    public void confirmPlayerJoined(Integer number) {
        players.get(number).setJoined();
        NetworkManager.networkManager.addEventToSend(new Event("lobby clearLobby"));
        for (PlayerLobbyIcon p: players) {
            NetworkManager.networkManager.addEventToSend(new Event("lobby addNewPlayer String " + NetworkApi.manager.myAddress.ip + " int " + NetworkApi.manager.myAddress.port + " String " + p.isAwaiting));
        }
    }

    public void clearLobby() {
        for (PlayerLobbyIcon p: this.players) {
            removeActor(p);
        }
        this.players.clear();
    }
}
