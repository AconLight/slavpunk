package assets;

import boost.GameObject;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.applet.AudioClip;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetLoader {
    private static HashMap<String, Texture> assets = new HashMap<>();
    private static HashMap<String, HashMap<String, Animation<TextureRegion>>> animations = new HashMap<>();

    public static Music soundtrack, soundtrack_menu;
    public static Sound pistol, jump, swap, end, gun, tarcza, cannonStart;



    public static SpriteObject getAsset(String name) {
        SpriteObject gameObject = new SpriteObject();
        Texture tex = assets.get(name);
        gameObject.addActor(new Image(tex));
        gameObject.setBounds(0, 0, tex.getWidth(), tex.getHeight());
        return gameObject;
    }

    public static SpriteObject getAnimation(String name, float index) {
        SpriteObject gameObject = new SpriteObject(index);
        for (String key : animations.get(name).keySet()) {
            TextureRegion tex = animations.get(name).get(key).getKeyFrames()[0];
            gameObject.setBounds(0, 0, tex.getRegionWidth(), tex.getRegionHeight());
            break;
        }
        gameObject.animations.putAll(animations.get(name));
        return gameObject;
    }

    public static SpriteObject getAnimation(String name) {
        SpriteObject gameObject = new SpriteObject();
        for (String key : animations.get(name).keySet()) {
            TextureRegion tex = animations.get(name).get(key).getKeyFrames()[0];
            gameObject.setBounds(0, 0, tex.getRegionWidth(), tex.getRegionHeight());
            break;
        }
        gameObject.animations.putAll(animations.get(name));
        return gameObject;
    }

    public static SpriteObject getAsset(String name, float index) {
        SpriteObject gameObject = new SpriteObject(index);
        Texture tex = assets.get(name);
        gameObject.addActor(new Image(tex));
        gameObject.setBounds(0, 0, tex.getWidth(), tex.getHeight());
        return gameObject;
    }

    public static void createAsset(String path, String name) {
        Gdx.app.log("createAsset", path);
        assets.put(name, new Texture(Gdx.files.internal(path)));
    }

    public static void load() {
        createAsset("graphics/sprites/add_player.png", "add_player");
        createAsset("graphics/sprites/awaiting.png", "awaiting");
        createAsset("graphics/sprites/player_joined.png", "player_joined");
        createAsset("graphics/sprites/accept.png", "accept");
        createAsset("graphics/sprites/proj.png", "proj");

        createAsset("red.png", "redartedSplash");
        createAsset("graphics/sprites/menu_bg.png", "bgSplash");
        createAsset("square.png", "square");
        createAsset("graphics/sprites/quit.png", "quit");
        createAsset("graphics/sprites/play.png", "play");
        createAsset("graphics/sprites/background.png", "bg");
        createAsset("graphics/sprites/platform0.png", "platform1");
        createAsset("graphics/sprites/shot.png", "shot");
        createAsset("graphics/sprites/pistol.png", "pistol");
        createAsset("graphics/sprites/aim.png", "aim");
        createAsset("graphics/sprites/shield.png", "shield");
        createAsset("graphics/sprites/pistolLeft.png", "pistolLeft");
        createAsset("graphics/sprites/shotgun.png", "shotgun");
        createAsset("graphics/sprites/shotgunLeft.png", "shotgunLeft");
        createAsset("graphics/sprites/blood.png", "blood");
        createAsset("graphics/sprites/leftWins.png", "leftWins");
        createAsset("graphics/sprites/rightWins.png", "rightWins");
        createAsset("graphics/sprites/light.png", "light");
        createAsset("graphics/sprites/howto.png", "howto");
        createAsset("graphics/sprites/ship.png", "ship");
        createAsset("graphics/sprites/elevator.png", "elevator");
        createAsset("graphics/sprites/vacuum.png", "vacuum");
        createAsset("graphics/sprites/pipes.png", "pipes");
        createAsset("graphics/sprites/stock.png", "cannonBase");
        createAsset("graphics/sprites/cannon.png", "cannon");
        createAsset("graphics/sprites/background.png", "background");
        createAsset("graphics/sprites/part.png", "part");
        createAsset("graphics/sprites/gameover.png", "gameover");
        createAsset("graphics/sprites/boosters.png", "boosters");

        loadAnimations();

        soundtrack = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundtrack.wav"));
        soundtrack_menu = Gdx.audio.newMusic(Gdx.files.internal("sounds/soundtrack_menu.wav"));

        pistol = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.wav"));
        jump = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3"));
        swap = Gdx.audio.newSound(Gdx.files.internal("sounds/swap.mp3"));
        end = Gdx.audio.newSound(Gdx.files.internal("sounds/end.mp3"));
        gun = Gdx.audio.newSound(Gdx.files.internal("sounds/gun.mp3"));
        tarcza = Gdx.audio.newSound(Gdx.files.internal("sounds/tarcza.mp3"));
        cannonStart = Gdx.audio.newSound(Gdx.files.internal("sounds/getPistol.wav"));

        //wywolanie sound AssetLoader.[nazwa].play();

    }


    public static void createAnimation(String path, String name) {
        HashMap<String, Animation<TextureRegion>> myAnimations = new HashMap<>();

        List<String> dirs = getDirs("graphics/animations/" + path);
        Gdx.app.log("dupa", "dupa");
        for (String child : dirs) {
            Gdx.app.log("AssetLoader", child);
            List<String> dirs2 = getFiles(child);
            TextureRegion[] textures = new TextureRegion[dirs2.size()];
            int i = 0;
            for (String childTexture : dirs2) {
                Gdx.app.log("AssetLoader ------  ", childTexture);
                textures[i] = new TextureRegion(new Texture(childTexture));
                i++;
            }
            Animation<TextureRegion> animation = new Animation<TextureRegion>(1f, textures);
            animation.setPlayMode(Animation.PlayMode.LOOP);
            String[] parts = child.split("/");

            myAnimations.put(parts[parts.length - 1], animation);
        }

        animations.put(name, myAnimations);
    }

    public static void loadAnimations() {
        System.out.println("siema");
        getDirs(".");
        createAnimation("jade", "jade");
        createAnimation("head", "head");
        createAnimation("legs", "legs");
        createAnimation("body", "body");
        createAnimation("small", "small");
        createAnimation("platform1", "platform1");

        createAnimation("head", "head");
        createAnimation("arm", "arm");
        createAnimation("body", "body");
        createAnimation("legs", "legs");
        createAnimation("hamer", "hamer");

        createAnimation("enemy_head", "enemy_head");
        createAnimation("enemy_body", "enemy_body");
        createAnimation("enemy_legs", "enemy_legs");
        createAnimation("enemy_eye", "enemy_eye");
        createAnimation("enemy_renka", "enemy_renka");
        createAnimation("enemy_weapon", "enemy_weapon");



    }


    public static List<String> getDirs(String path) {
        List<String> dirs = new ArrayList<>();
        System.out.println("current path: " + Paths.get(".").toAbsolutePath());
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            dirs = walk.filter(Files::isDirectory)
                    .map(x -> path + "/" + x.getFileName().toString()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        dirs.remove(0);
        System.out.println(dirs);
        return dirs;
    }

    public static List<String> getFiles(String path) {
        List<String> dirs = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(path).toAbsolutePath())) {

            dirs = walk.filter(Files::isRegularFile)
                    .map(x -> path + "/" + x.getFileName().toString()).collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dirs;
    }


}
