package com.mygdx.gameObjects;

import assets.AssetLoader;
import boost.GameComponent;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.mygdx.gameComponents.MovementDrag;
import com.mygdx.gameComponents.RotaryMovementDrag;
import com.mygdx.gameComponents.Movement;
import com.mygdx.gameComponents.RotaryMovement;

public class Test extends GameObject {

    GameObject s1, s2;
    SpriteObject jade;
    Movement movement;
    MovementDrag movementDrag;
    RotaryMovement rotaryMovement;
    RotaryMovementDrag rotaryMovementDrag;

    public Test() {
        super();
        s1 = AssetLoader.getAsset("square", 10);
        s2 = AssetLoader.getAsset("square");
        jade = AssetLoader.getAnimation("jade", 20);
        jade.chooseAnimation("idle");
        jade.setScale(10);
        // jade.setShader("invert");
        jade.setPosition(-600, 60);
        s2.setPosition(1200, 200);
        s1.setPosition(1100, 100);
        this.setPosition(500, 200);
        addActor(jade);
        addActor(s1);
        addActor(s2);
        movement = new Movement(this);
        movementDrag = new MovementDrag(movement, 0.995f);
        rotaryMovement = new RotaryMovement(this);
        rotaryMovementDrag = new RotaryMovementDrag(rotaryMovement, 0.995f);
        rotaryMovement.alfaVelocity = 0f;
        movement.velocity.set(20, 0);
        addComponent(movement);
        //addComponent(rotaryMovement);
        addComponent(movementDrag);
        //addComponent(rotaryMovementDrag);
    }

    float time = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta/10;
//        jade.shader.begin();
//        jade.shader.setUniformf("time", time/10);
//        jade.shader.end();
    }
}
