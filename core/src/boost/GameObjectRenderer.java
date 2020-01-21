package boost;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameObjectRenderer {

    public Stage stage;

    public GameObjectRenderer(Stage stage) {
        this.stage = stage;
    }
    public void render() {
        for (float index: GameObjectManager.indexes) {
            GameObjectManager.currentIndex = index;
            stage.draw();
        }
    }


}
