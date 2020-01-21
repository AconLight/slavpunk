package boost;

import java.util.ArrayList;

public abstract class GameComponent {
    public ArrayList<GameComponent> componentsAfter, componentsBefore;

    protected GameObject gameObject;

    public GameComponent()
    {
        componentsBefore = new ArrayList<>();
        componentsAfter = new ArrayList<>();
    }

    public void act(float delta) {

    }

    public void actAll(float delta) {
        actBefore(delta);
        act(delta);
        actAfter(delta);
    }

    public void actBefore(float delta) {
        for (GameComponent component: componentsBefore) {
            component.act(delta);
        }
    }

    public void actAfter(float delta) {
        for (GameComponent component: componentsAfter) {
            component.act(delta);
        }
    }
}
