package com.mygdx.gameComponents;

import boost.GameComponent;
import com.badlogic.gdx.math.Vector2;

public class MovementDrag extends GameComponent {

    public float drag;

    private Movement movement;
    public MovementDrag(Movement movement, float drag) {
        super();
        this.movement = movement;
        this.drag = drag;
    }

    @Override
    public void act(float delta) {
        movement.velocity.set(movement.velocity.x*drag, movement.velocity.y*drag);
    }


}
