package com.mygdx.gameComponents;

import boost.GameComponent;
import boost.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class RotaryMovementDrag extends GameComponent {

    public float drag;

    private RotaryMovement rotaryMovement;
    public RotaryMovementDrag(RotaryMovement rotaryMovement, float drag) {
        super();
        this.rotaryMovement = rotaryMovement;
        this.drag = drag;
    }

    @Override
    public void act(float delta) {
        rotaryMovement.alfaVelocity *= drag;
    }


}
