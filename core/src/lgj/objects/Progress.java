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

public class Progress {
    float scale;
    ProgressBar bar;
    Skin skin;

    public Progress(int x, int y, int width, int height, Color color) {

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
        bar.setPosition(x, y);
        bar.setValue(5);
        bar.setZIndex(7);

    }

    public ProgressBar getBar() {
        bar.remove();
        return bar;
    }
}
