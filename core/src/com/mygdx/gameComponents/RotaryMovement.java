package com.mygdx.gameComponents;

import boost.GameComponent;
import boost.GameObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class RotaryMovement extends GameComponent {

    public Float alfa;
    public Float alfaVelocity;
    public Float alfaAcceleration;

    public RotaryMovement(GameObject gameObject) {
        super();
        this.gameObject = gameObject;
        alfa = gameObject.getRotation();
        alfaVelocity = 0f;
        alfaAcceleration = 0f;
    }

    @Override
    public void act(float delta) {
        alfaVelocity += alfaAcceleration * delta;
        alfa += alfaVelocity * delta;
        gameObject.setRotation(alfa);
    }


}
