package boost;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.HashMap;

public class SpriteObject extends GameObject {

    public HashMap<String, Animation<TextureRegion>> animations;

    public Animation<TextureRegion> currentAnimation;


    public Color color;

    private float time;

    public SpriteObject() {
        super();
        animations = new HashMap<>();
        time = 0;
        color = Color.WHITE;
    }

    public SpriteObject(float index) {
        super(index);
        animations = new HashMap<>();
        time = 0;
        color = Color.WHITE;
    }

    public void chooseAnimation(String name) {
        currentAnimation = animations.get(name);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        time += delta*20;
    }


    @Override
    public void draw(Batch batch, float parentAlfa) {
        batch.setShader(shader);
        batch.setColor(color);
        if (GameObjectManager.currentIndex == index) {
            super.draw(batch, parentAlfa * alfa);
            if (currentAnimation != null) {
                batch.draw(currentAnimation.getKeyFrame(time), getX() + currentAnimation.getKeyFrame(time).getRegionWidth() / 2 * getScaleX(),
                        getY() + currentAnimation.getKeyFrame(time).getRegionHeight() / 2 * getScaleY(),
                        currentAnimation.getKeyFrame(time).getRegionWidth() / 2, currentAnimation.getKeyFrame(time).getRegionHeight() / 2,
                        currentAnimation.getKeyFrame(time).getRegionWidth(), currentAnimation.getKeyFrame(time).getRegionHeight(),
                        getScaleX(), getScaleY(), getRotation());
            }
        }
    }

    public float getAbsoluteX() {
        float x = getX();
        Actor p = getParent();
        while (p != null) {
            x += p.getX();
            p = p.getParent();
        }
        return x;
    }

    public float getAbsoluteY() {
        float x = getY();
        Actor p = getParent();
        while (p != null ) {
            x += p.getY();
            p = p.getParent();
        }
        return x;
    }
}
