package boost;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

public class GameObject extends Group {
    public float index;
    public float alfa = 1f;

    public ShaderProgram shader;

    private ArrayList<GameComponent> components;

    public GameObject() {
        create(0);
    }

    public GameObject(float index) {
        create(index);
    }

    public void create(float index) {
        this.index = index;
        GameObjectManager.registerCreationOfGameObject(this);
        ShaderProgram shader = new ShaderProgram(Gdx.files.internal("shaders/example.vert"), Gdx.files.internal("shaders/example.frag"));
        setShader(shader);
        components = new ArrayList<>();
    }

    public void setShader(ShaderProgram shader) {
        this.shader = shader;
    }

    public void setShader(String name) {
        this.shader = new ShaderProgram(Gdx.files.internal("shaders/" + name + ".vert"), Gdx.files.internal("shaders/" + name + ".frag"));
        Gdx.app.log(name, "is compiled: " + shader.isCompiled());
    }

    public void addComponent(GameComponent component) {
        components.add(component);
    }

    public void destroy() {
        // TODO
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        for (GameComponent component: components) {
            component.actAll(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        batch.setShader(shader);
        if (GameObjectManager.currentIndex == index) {
            super.draw(batch, parentAlfa * alfa);
        }
        else if (this instanceof GameObject) {
            drawChildren(batch, parentAlfa * alfa);
        }
    }
}
