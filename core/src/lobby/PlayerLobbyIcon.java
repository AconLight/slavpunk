package lobby;

import assets.AssetLoader;
import boost.GameObject;

/**
 * Created by Wojciech on 2020-01-23.
 */
public class PlayerLobbyIcon extends GameObject{

    public int number;
    public boolean isAwaiting = false;

    public PlayerLobbyIcon(boolean isAwaiting, int number) {
        this.number = number;
        setPosition(400, number*270 + 300);
        if (isAwaiting) {
            setAwaiting();
        } else {
            setJoined();
        }
    }

    public void setAwaiting() {
        isAwaiting = true;
        clearChildren();
        addActor(AssetLoader.getAsset("awaiting"));
    }

    public void setJoined() {
        isAwaiting = false;
        clearChildren();
        addActor(AssetLoader.getAsset("player_joined"));
    }
}
