package boost;

import java.util.HashMap;
import java.util.HashSet;

public class GameObjectManager {
    public static HashSet<Float> indexes;
    public static HashMap<String, GameObject> gameObjects;
    public static String lastIdx = "0";
    public static float currentIndex;

    public static void create() {
        indexes = new HashSet<>();
        gameObjects = new HashMap<>();
    }

    public static void registerCreationOfGameObject(GameObject gameObject) {
        indexes.add(gameObject.index);
        int tempId = Integer.parseInt(lastIdx);
        tempId++;
        lastIdx = "" + tempId;
        gameObjects.put(lastIdx, gameObject);
        gameObject.id = lastIdx;
    }

    public static void registerCreationOfGameObject(GameObject gameObject, String id) {
        indexes.add(gameObject.index);
        gameObjects.put(id, gameObject);
        gameObject.id = id;
    }


}
