package lobby;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.mygdx.events.Event;
import com.mygdx.gameComponents.Clickable;
import com.mygdx.networking.Address;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;
import com.mygdx.scenes.MySceneManager;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class AcceptObject extends GameObject {

    GameObject accept;

    Address host = null;
    int playersNumber = 0;

    public AcceptObject() {
        super(0, "accept");
        accept = AssetLoader.getAsset("accept");
        accept.addComponent(new Clickable(accept, (SpriteObject) accept, () -> accept()));
    }

    public void showAccept(String hostIp, Integer hostPort, Integer playersNumber) {
        clearChildren();
        addActor(accept);
        this.playersNumber = playersNumber;
        host = new Address(hostIp, hostPort);
    }

    public void accept() {
        Gdx.app.log("AcceptObject", "accepted");
        NetworkManager.networkManager.setHostAddress(host);
        NetworkManager.networkManager.addEventToSend(new Event("lobby confirmPlayerJoined int " + (playersNumber-1)));
        MySceneManager.switchToScene(MySceneManager.multiplayerWindow);
        //MySceneManager.multiplayerWindow.lobby.players.get(playersNumber-1).setJoined();
    }
}
