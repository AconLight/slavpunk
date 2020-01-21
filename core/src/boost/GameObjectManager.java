package boost;

import java.util.HashSet;

public class GameObjectManager {
    public static HashSet<Float> indexes;
    public static float currentIndex;

    public static void create() {
        indexes = new HashSet<>();
    }

    public static void registerCreationOfGameObject(GameObject gameObject) {
        indexes.add(gameObject.index);
    }


}
