package lgj.objects;

import assets.AssetLoader;
import boost.GameObject;
import boost.GameObjectManager;
import boost.SpriteObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.events.Event;
import com.mygdx.networking.NetworkApi;
import com.mygdx.networking.NetworkManager;



public class Player extends GameObject {

    SpriteObject head, body, arm, legs, hamer;
    boolean isMine;
    float posX, posY, scale, velocity;
    String state, isRight;
    String isStrike = "not";
    float gravity = 0;
    boolean isInCannon = false;
    Vacuum vacuum;
    Progress progress;

    final int elevatorLeft = 415;
    final int elevatorRight = 560;

    Camera cam;

    Vector2 camOffset;
    Vector2 camConstOffset;
    Vector2 camDirection;
    float camZoom;
    float camDirectionZoom;

    Boolean isOnVacuum;

    CannonBase current;

    public int parts;

    public Player(Stage stage, boolean isMine, String id) {
        super(5, id);
        parts = 0;
        this.isMine = isMine;
        isOnVacuum = false;
        camOffset = new Vector2();
        camConstOffset = new Vector2(100, 120);
        camDirection = new Vector2();
        camZoom = 2;
        camDirectionZoom = 2;
        cam = stage.getCamera();
        progress = new Progress(true, "health" + id);

        posX = 300;
        posY = 485;
        scale = 4;
        velocity = 10;
        isRight = "right";
        state = "idle";
        animationInit();

        addActor(hamer);
        addActor(head);
        addActor(body);
        addActor(arm);
        addActor(legs);
        addActor(progress.bar);
    }

    void animationInit() {
        head = AssetLoader.getAnimation("head", index);
        head.animations.get("head_right").setFrameDuration(10f);
        head.animations.get("head_left").setFrameDuration(10f);
        head.animations.get("idle_right").setFrameDuration(10f);
        head.animations.get("idle_left").setFrameDuration(10f);
        head.chooseAnimation("idle_right");
        head.setScale(scale);

        body = AssetLoader.getAnimation("body", index);
        body.chooseAnimation("idle_right");
        body.setScale(scale);

        arm = AssetLoader.getAnimation("arm", index);
        arm.animations.get("idle_right").setFrameDuration(40f);
        arm.animations.get("idle_left").setFrameDuration(40f);
        arm.animations.get("arm_right").setFrameDuration(2f);
        arm.animations.get("arm_left").setFrameDuration(2f);
        arm.chooseAnimation("idle_right");
        arm.setScale(scale);

        hamer = AssetLoader.getAnimation("hamer", index);
        hamer.chooseAnimation("idle_right");
        hamer.setScale(scale);

        legs = AssetLoader.getAnimation("legs", index);
        legs.chooseAnimation("idle_right");
        legs.setScale(scale);
    }

    /**
     * zeby to dzialao, to nazwy folderków muszą być w konwencji:
     * arm_right -> bieganie w prawo ręka bez młotka;
     * idle_left -> stanie w lewo
     * arm - zwykla reka
     * hamer - reka z młotkiem
     * dupa - na tinderze
     *
     * @param state
     */
    public void changeAnimation(String state, String isRight, String isStrike) {

        switch (state) {             //if w switchu nice mmmmm
            case "idle": {

                head.chooseAnimation("idle_" + isRight);
                arm.chooseAnimation("idle_" + isRight);
                hamer.chooseAnimation("idle_" + isRight);
                body.chooseAnimation("idle_" + isRight);
                legs.chooseAnimation("idle_" + isRight);
                break;
            }
            default: {
                head.chooseAnimation("head_" + state);
                arm.chooseAnimation("arm_" + state);
                hamer.chooseAnimation("hamer_" + state);
                body.chooseAnimation("body_" + state);
                legs.chooseAnimation("legs_" + state);
            }
        }

        if (isStrike.equals("isStrike")) {
            startStriking(isRight);
        } else {
            stopStriking(isRight);
        }
    }

    public void startStriking(String isRight) {
        hamer.chooseAnimation("strike_" + isRight);
    }

    public void stopStriking(String isRight) {
        hamer.chooseAnimation("idle_" + isRight);
    }

    private String prevStrike = "", prevState = "", prevRight = "";

    boolean canPick = false;
    boolean canFix = false;

    public void act(float delta) {
        super.act(delta);

        prevRight = isRight;
        prevState = state;
        prevStrike = isStrike;

        boolean isInElevator = (posX >= elevatorLeft && posX <= elevatorRight);

        if (isMine) {

            if (Gdx.input.isKeyPressed(Input.Keys.F)) {
                isStrike = "isStrike";
            } else {
                isStrike = "not";
            }

            Boolean isPresedWSAD = false;
            if (!isInCannon) {
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    if (isInElevator) {
                        posY += velocity;
                    } else if (posY == 485 || posY == 790 || posY == 1097) {
                        gravity -= 15;
                    }
                    //posY += velocity;
                    //state = "up";
                    isPresedWSAD = true;
                    camDirection.set(camDirection.x, +200);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                    posY -= velocity;
                    //state = "down";
                    isPresedWSAD = true;
                    camDirection.set(camDirection.x, -200);
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                posX += velocity;
                isRight = "right";
                state = "right";
                isPresedWSAD = true;
                camDirection.set(200, camDirection.y);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                posX -= velocity;
                isRight = "left";
                state = "left";
                isPresedWSAD = true;
                camDirection.set(-200, camDirection.y);
            }

            if (!isPresedWSAD) {
                state = "idle";
                camDirection.set(0, 0);
            }

            if (isStrike.equals("isStrike")) {
                if (hamer.animations.get("strike_" + isRight).getKeyFrameIndex(hamer.time) < 7) {
                    canPick = true;
                    canFix = true;
                }
                if (hamer.animations.get("strike_" + isRight).getKeyFrameIndex(hamer.time) == 7) {
                    if (isOnVacuum && canPick) {
                        vacuum.pickPart(id);
                        NetworkManager.networkManager.addEventToSend(new Event("vacuum pickPart String " + id));
                    }
                    if (isInCannon && canFix) {
                        if (parts > 0) {
                            current.fix(id);
                            NetworkManager.networkManager.addEventToSend(new Event(current.id + " fix String " + id));
                        }
                    }
                    canPick = false;
                    canFix = false;
                }
            }
        }


        if (!isInElevator) {
            gravity += 1;
            posY -= gravity;
        } else {
            posY -= 2;
            state = "idle";
            camDirection.set(0, 200);
        }


        if (posY < 485) {
            posY = 485;
            gravity = 0;
        } else if (posY > 1130) {
            posY = 1130;
        } else if (!isInElevator) {
            if (posY >= 720 && posY < 790) {
                posY = 790;
                gravity = 0;
            }
            if (posY >= 1020 && posY < 1097) {
                posY = 1097;
                gravity = 0;
            }
        }

        // Horizontal borders
        if (posY < 720) { // First floor
            if(vacuum == null) {
                vacuum = (Vacuum) GameObjectManager.gameObjects.get("vacuum");
                vacuum.parts.add(3);
                vacuum.playerIds.add(id);
            }
            if (posX > 770 && posX < 940) {
                if (!isOnVacuum) {
                    isOnVacuum = true;
                    vacuum.setOn();
                }
            } else {
                if (isOnVacuum) {
                    isOnVacuum = false;
                    vacuum.setOff();
                }
            }
            if (posX < 270) {
                posX = 270;
            } else if (posX >= 1555) {
                posX = 1555;
                if(!isInCannon) {
                    current = (CannonBase) GameObjectManager.gameObjects.get("cannonBase1");
                    current.use(id);
                    isInCannon = true;
                }
            } else {
                if(isInCannon) {
                    ((CannonBase) GameObjectManager.gameObjects.get("cannonBase1")).leave(id);
                    isInCannon = false;
                    current = null;
                }
            }
        } else if (posY < 1090) { // Second floor
            if (posX < 315) {
                posX = 315;
            } else {
                if (posX >= 1635) {
                    posX = 1635;
                    if(!isInCannon) {
                        current = (CannonBase) GameObjectManager.gameObjects.get("cannonBase2");
                        current.use(id);
                        isInCannon = true;
                    }
                } else {
                    if(isInCannon) {
                        ((CannonBase) GameObjectManager.gameObjects.get("cannonBase2")).leave(id);
                        isInCannon = false;
                        current = null;
                    }
                }
            }
        } else { // Third floor
            if (posX < 300) {
                posX = 300;
            } else {
                if (posX >= 1710){
                    posX = 1710;
                    if(!isInCannon) {
                        current = (CannonBase) GameObjectManager.gameObjects.get("cannonBase3");
                        current.use(id);
                        isInCannon = true;
                    }
                } else {
                    if(isInCannon) {
                        ((CannonBase) GameObjectManager.gameObjects.get("cannonBase3")).leave(id);
                        isInCannon = false;
                        current = null;
                    }
                }
            }
        }

        updatePos();

        if (isMine) {
            sendPos();
            changeAnimation(state, isRight, isStrike);
            if (!prevStrike.equals(isStrike) || !prevRight.equals(isRight) || !prevState.equals(state))
                sendAnimationUpdate(isStrike);

            if (isInCannon) {
                camDirection.set(1000, 0);
                camDirectionZoom = 3f;
            } else {
                camDirectionZoom = 2f;
            }
            camZoom = (camZoom + camDirectionZoom*delta)/(1+delta);
            camOffset = camOffset.set((camDirection.x * delta + camOffset.x) / (1+delta), (camDirection.y * delta + camOffset.y) / (1+delta));
            cam.translate(-cam.position.x + posX + camOffset.x + camConstOffset.x, -cam.position.y + posY + camOffset.y + camConstOffset.y, 0);
            ((OrthographicCamera) cam).zoom = camZoom;
            cam.update();
        }
    }

    public void updatePos() {
        float x = getX();
        float y = getY();
        setPosition((posX + x*5) / 6, (posY + y*5) / 6);
    }

    private float lastSendX = posX;
    private float lastSendY = posY;

    public void sendPos() {
        float dx = posX - lastSendX;
        float dy = posY - lastSendY;
        if (dy * dy + dx * dx > 100) {
            lastSendX = posX;
            lastSendY = posY;
            NetworkManager.networkManager.addEventToSend(new Event(id + " updatePos float " + posX + " float " + posY));
        }
    }

    void sendAnimationUpdate(String isStrike) {
        Gdx.app.log("Player", "sendAnimationUpdate");
        NetworkManager.networkManager.addEventToSend(new Event(id + " changeAnimation String " + state + " String " + isRight + " String " + isStrike));
    }

    public void updatePos(Float posX2, Float posY2) {
        posX = posX2;
        posY = posY2;
    }

}
