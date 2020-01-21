package com.mygdx.gameComponents;

import boost.GameComponent;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Clickable extends GameComponent {

    @FunctionalInterface
    public interface ClickAction {
        void action();
    }
    public Clickable(GameObject gameObject, SpriteObject spriteObject, ClickAction clickAction) {
        super();
        this.gameObject = gameObject;
        Actor img = spriteObject.getChildren().get(0);
        if (img != null) {
            gameObject.setBounds(0, 0, img.getWidth(), img.getHeight());
        }
        gameObject.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button)
            {
                clickAction.action();
                return true;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }


}
