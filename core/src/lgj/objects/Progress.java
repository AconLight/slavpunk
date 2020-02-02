package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Progress extends GameObject {

    SpriteObject ship;
    boolean isMine;
    float posX, posY, scale;
    float value;
    ProgressBar bar;
    Skin skin;

    public Progress(boolean isMine, String id, int x, int y, int width, int height, Color color) {
        super(3, id);
        this.isMine = isMine;

        setPosition(x,y);
        posX = x;
        posY = y;
        scale = 8;
        skin = new Skin();
        Pixmap pixmap = new Pixmap(10, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), skin.newDrawable("white", color));
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 1, 0.01f, false, barStyle);
        bar.setWidth(width);
        bar.setHeight(height);
        bar.setPosition(x,y);
        bar.setValue(5);
    }

    public void act(float delta) {
        super.act(delta);
        updatePos();
        Gdx.app.log("progress bar", id);
    }

    public ProgressBar getBar() {return bar;}

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x) / 2, (posY + y) / 2);
    }
}
