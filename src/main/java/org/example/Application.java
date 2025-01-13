package org.example;


import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.*;
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.shader.VarType;
import com.jme3.texture.Texture;

import java.util.Arrays;

public class Application extends SimpleApplication {
    private BitmapText ch; // cross-hair plus

    /** Prepare Movement booleans */
    private boolean forward = false;
    private boolean backward = false;
    private boolean left = false;
    private boolean right = false;
    private Vector3f walkDirection = Vector3f.ZERO;

    /** player variables */
    private static CharacterControl playerControl;

    /** window size storage */
    private int lastHeight = 0;
    private int lastWidth = 0;

    /** Furniture */
    private Furniture couch;
    private Furniture table;
    private Furniture shelf;

    /** States */
    private BulletAppState bulletAppState;
    private MenuState menuState;
    private PreviewHandler previewHandler;
    private Material selectedMaterial;
    private Furniture selectedFurniture;

    /** Utilities */
    private Materials materials;

    private static final Box floor;
    static {
        floor = new Box(50f, 0.1f, 50f);
        floor.scaleTextureCoordinates(new Vector2f(3, 6));
    }

    @Override
    public void simpleUpdate(float tpf) {
        // changes related to resizing the window
        checkWindowSize();

        move();
        makePreview();
    }

    @Override
    public void simpleInitApp() {

        /* Initialize the app (jMonkey) */
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        /* create the Menu State (Help Menu) */
        menuState = new MenuState();
        stateManager.attach(menuState);
        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");

        initPlayer();
        initInputs();

        initMaterialManager();
        previewHandler = new PreviewHandler(this); // no need for extra method

        /* init game scene */
        initWalls();
        initFloor();

        /* init UI */
        initCrossHairs();

        /* init furniture handling */
        this.couch = new Couch(assetManager);
        this.table = new Table(assetManager);
        this.shelf = new Shelf(assetManager);

        this.selectedFurniture = this.table; // default furniture
    }

    private void makePreview() {
        if (previewHandler.isPreviewing()) {
            previewHandler.moveObjectWithRayCasting(cam.getLocation(), cam.getDirection().normalize());
        }
    }

    private void move() {
        walkDirection.set(0,0,0);
        if (forward) {
            walkDirection.addLocal(cam.getDirection().mult(0.6f)); // TODO: should be from a speed constant
        }
        if (backward) {
            walkDirection.addLocal(cam.getDirection().mult(-0.6f));
        }
        if (left) {
            walkDirection.addLocal(cam.getLeft().mult(0.6f));
        }
        if (right) {
            walkDirection.addLocal(cam.getLeft().mult(-0.6f));
        }
        // neutralize the y axis
        walkDirection = walkDirection.mult(new Vector3f(1f, 0 ,1f));

        playerControl.setWalkDirection(walkDirection);
        cam.setLocation(playerControl.getPhysicsLocation().add(0, 6f, 0));
    }

    private void checkWindowSize() {
        int width = settings.getWidth();
        int height = settings.getHeight();

        // Check if the size has changed since the last frame
        if (width != lastWidth || height != lastHeight) {
            // recreate cross-hair in the centre
            this.guiNode.detachChild(ch);
            initCrossHairs();
        }
        lastHeight = height;
        lastWidth = width;
    }

    private void initPlayer() {
        // Create a collision shape for the player
        CapsuleCollisionShape capsuleShape = new CapsuleCollisionShape(1.5f, 6f, 1);
        playerControl = new CharacterControl(capsuleShape, 0.05f); // Step height
        playerControl.setJumpSpeed(20);
        playerControl.setFallSpeed(60);
        playerControl.setGravity(30);

        playerControl.setPhysicsLocation(new Vector3f(0, 10, 0));

        bulletAppState.getPhysicsSpace().add(playerControl);
    }

    private void initMaterialManager() {
        this.materials = new Materials(assetManager);
        this.selectedMaterial = this.materials.STONE();
    }

    private void initInputs() {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Place", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Remove", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addMapping("Preview", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("RotatePreviewL", new KeyTrigger(KeyInput.KEY_Z));
        inputManager.addMapping("RotatePreviewR", new KeyTrigger(KeyInput.KEY_X));
        inputManager.addMapping("RotatePreviewScrollL", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        inputManager.addMapping("RotatePreviewScrollR", new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        inputManager.addMapping("ToggleMenu", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addMapping("1", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("2", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("3", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("4", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("5", new KeyTrigger(KeyInput.KEY_5));
        inputManager.addMapping("6", new KeyTrigger(KeyInput.KEY_6));
        inputManager.addMapping("7", new KeyTrigger(KeyInput.KEY_7));
        inputManager.addMapping("c", new KeyTrigger(KeyInput.KEY_C));
        inputManager.addMapping("v", new KeyTrigger(KeyInput.KEY_V));
        inputManager.addMapping("b", new KeyTrigger(KeyInput.KEY_B));
        inputManager.addMapping("n", new KeyTrigger(KeyInput.KEY_N));
        inputManager.addMapping("m", new KeyTrigger(KeyInput.KEY_M));
        inputManager.addMapping("f", new KeyTrigger(KeyInput.KEY_F));
        inputManager.addListener(rotationListener, "RotatePreviewL", "RotatePreviewR");
        inputManager.addListener(analogListener, "RotatePreviewScrollL", "RotatePreviewScrollR");
        inputManager.addListener(actionListener, "Place", "Preview", "Remove");
        inputManager.addListener(movementListener, "Forward", "Backward", "Left", "Right", "Jump");
        inputManager.deleteMapping("FLYCAM_ZoomOut");
        inputManager.deleteMapping("FLYCAM_ZoomIn");
        inputManager.addListener(keyboardListener, "ToggleMenu", "1", "2", "3", "4", "5", "6", "7", "c", "v", "b", "n", "m", "f");
    }

    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("RotatePreviewScrollL")) {
                previewHandler.rotatePreview(0.1f);
            } else if (name.equals("RotatePreviewScrollR")) {
                previewHandler.rotatePreview(-0.1f);
            }
        }
    };


    final private ActionListener rotationListener = (String name, boolean keyPressed, float tpf) -> {
        if (!keyPressed) {
            this.previewHandler.rotatePreview(0.1f * (name.equals("RotatePreviewL") ? 1f : -1f));
        }
    };

    private final ActionListener keyboardListener = (String name, boolean keyPressed, float tpf) -> {
        if (!keyPressed && name.equals("ToggleMenu")) {
            menuState.toggleMenu();
        }
        if (!keyPressed && name.equals("1")) {
            this.selectedFurniture = couch;
            this.previewHandler.setPreviewObject(couch.getModel(), couch.getDefaultScale(), couch.getCompensationVector());
            this.flushAfterFurnitureSelection();
        }
        if (!keyPressed && name.equals("2")) {
            this.selectedFurniture = table;
            this.previewHandler.setPreviewObject(table.getModel(), table.getDefaultScale(), table.getCompensationVector());
            this.flushAfterFurnitureSelection();
        }
        if (!keyPressed && name.equals("3")) {
            this.selectedFurniture = shelf;
            this.previewHandler.setPreviewObject(shelf.getModel(), shelf.getDefaultScale(), shelf.getCompensationVector());
            this.flushAfterFurnitureSelection();
        }
        if (!keyPressed && name.equals("c")) {
            this.selectedMaterial = this.materials.WHITE();
        }
        if (!keyPressed && name.equals("v")) {
            this.selectedMaterial = this.materials.GREEN();
        }
        if (!keyPressed && name.equals("b")) {
            this.selectedMaterial = this.materials.RED();
        }
        if (!keyPressed && name.equals("n")) {
            this.selectedMaterial = this.materials.BLUE();
        }
        if (!keyPressed && name.equals("m")) {
            this.selectedMaterial = this.materials.YELLOW();
        }
        if (!keyPressed && name.equals("f")) {
            this.selectedMaterial = this.materials.STONE();
        }
    };

    private void flushAfterFurnitureSelection() {
        this.previewHandler.cancelPreview();
        this.previewHandler.toggle();
        if (menuState.visible()) menuState.toggleMenu();
    }

    final private ActionListener movementListener = (String name, boolean keyPressed, float tpf) -> {
        switch (name) {
            case "Forward":
                this.forward = keyPressed;
                break;
            case "Backward":
                this.backward = keyPressed;
                break;
            case "Left":
                this.left = keyPressed;
                break;
            case "Right":
                this.right = keyPressed;
                break;
            case "Jump":
                if (keyPressed) {
                    playerControl.jump();
                }
                break;
            default:
                break;
        }
    };

    public void handlePreview() {
        this.previewHandler.toggle();
        if (!this.previewHandler.isPreviewing()) {
            this.previewHandler.cancelPreview();
        } else {
            this.previewHandler.setPreviewObject(selectedFurniture.getModel(), selectedFurniture.getDefaultScale(), selectedFurniture.getCompensationVector());
        }
    }

    final private ActionListener actionListener = (String name, boolean keyPressed, float tpf) -> {
        if (name.equals("Place") && !keyPressed) {
            if (previewHandler.isPreviewing()) placePreview();
        } else if (name.equals("Preview") && !keyPressed) {
            this.handlePreview();
        } else if (name.equals("Remove") && !keyPressed) {
            this.removeFurniture();
        }
    };

    public void removeFurniture() {
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());

        CollisionResults results = new CollisionResults();
        rootNode.collideWith(ray, results);

        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            Spatial hit = closest.getGeometry();
            if (hit != null && "yes".equals(hit.getUserData("removable"))) {
                if (hit.getControl(PhysicsControl.class) != null) {
                    bulletAppState.getPhysicsSpace().remove(hit.getControl(PhysicsControl.class));
                }
                hit.removeFromParent();
            }
        }
    }

    public void placePreview() {
        this.previewHandler.placeObject(this.selectedMaterial);
    }

    public void initFloor() {
        Geometry floor_geo = new Geometry("Floor", floor);
        Material mat = this.materials.FLOOR();

        // setting wrap repeat option to repeat the FLOOR texture on the floor
        Texture tex = mat.getTextureParam("ColorMap").getTextureValue();
        tex.setWrap(Texture.WrapMode.Repeat);
        mat.setTextureParam("ColorMap", VarType.Texture2D, tex);

        floor_geo.setMaterial(mat);
        floor_geo.setLocalTranslation(0, -0.1f, 0);
        this.rootNode.attachChild(floor_geo);

        RigidBodyControl floor_phy = new RigidBodyControl(0.0f);
        floor_phy.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01);
        floor_geo.addControl(floor_phy);
        bulletAppState.getPhysicsSpace().add(floor_phy);
    }

    public void initWalls() {
        Box wallNS = new Box(0.1f, 10f, 53f); // these are a bit to big, but the clipping can't be seen on the inside
        Box wallWE = new Box(53f, 10f, 0.1f);

        for (Vector3f pos : Arrays.asList(new Vector3f(-50.1f, 10, 0), new Vector3f(50, 10, 0), new Vector3f(0, 10, -50.1f), new Vector3f(1, 10, 50f))) {
            Box wall = Math.abs(pos.x) > 1 ? wallNS : wallWE;
            Geometry wall_geo = new Geometry("Wall", wall);
            wall_geo.setMaterial(this.materials.WALL());
            wall_geo.setLocalTranslation(pos);
            this.rootNode.attachChild(wall_geo);

            RigidBodyControl wall_phy = new RigidBodyControl(0.0f);
            wall_phy.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01);
            wall_geo.addControl(wall_phy);
            bulletAppState.getPhysicsSpace().add(wall_phy);
        }
    }

    protected void initCrossHairs() {
        setDisplayStatView(false);

        ch = new BitmapText(guiFont);
        ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(
                settings.getWidth() / 2f,
                settings.getHeight() / 2f,
                0
        );
        guiNode.attachChild(ch);
    }

    public BulletAppState getBulletAppState() {
        return this.bulletAppState;
    }

    public BitmapFont getGuiFont() {
        return this.guiFont;
    }
}
