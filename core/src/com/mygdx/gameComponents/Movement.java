package com.mygdx.gameComponents;

import boost.GameComponent;
import boost.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Movement extends GameComponent {

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 acceleration;

    public Movement(GameObject gameObject) {
        super();
        this.gameObject = gameObject;
        position = new Vector2(gameObject.getX(), gameObject.getY());
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
    }

    @Override
    public void act(float delta) {
        velocity.mulAdd(acceleration, delta);
        position.mulAdd(velocity, delta);
        gameObject.setPosition(position.x, position.y);
    }


}
