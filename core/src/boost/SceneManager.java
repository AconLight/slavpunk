package boost;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class SceneManager {
    private static ArrayList<MyScene> scenes, scenesToRemove, scenesToAdd;

    public void createSceneManager() {
        scenes = new ArrayList<>();
        scenesToAdd = new ArrayList<>();
        scenesToRemove = new ArrayList<>();
    }

    public static void addScene(MyScene scene) {
        scenesToAdd.add(scene);
    }

    public static void removeScene(MyScene scene) {
        scenesToRemove.add(scene);
    }

    public static void switchInputToScene(MyScene scene) {
        Gdx.input.setInputProcessor(scene.stage);
    }

    public static void switchToScene(MyScene scene) {
        scenesToRemove.addAll(scenes);
        scenesToAdd.add(scene);
        switchInputToScene(scene);
    }

    public void act() {
        for (MyScene scene: scenes) {
            scene.act();
        }
        scenes.removeAll(scenesToRemove);
        scenesToRemove.clear();
        scenes.addAll(scenesToAdd);
        scenesToAdd.clear();
    }
}
