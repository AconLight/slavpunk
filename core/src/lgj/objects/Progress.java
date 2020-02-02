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

    public Progress(boolean isMine, String id) {
        super(3, id);
        this.isMine = isMine;

        posX = 200;
        posY = 260;
        scale = 8;
        skin = new Skin();
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), skin.newDrawable("white", Color.RED));
        barStyle.knobBefore = barStyle.knob;
        bar = new ProgressBar(0, 10, 0.5f, false, barStyle);
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
