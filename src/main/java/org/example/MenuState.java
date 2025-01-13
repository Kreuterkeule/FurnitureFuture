package org.example;

import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

public class MenuState extends AbstractAppState {

    Application app;
    private Node guiNode;

    private BitmapText text;
    private boolean isVisible;
    private Geometry background;


    @Override
    public void initialize(AppStateManager stateManager, com.jme3.app.Application app) {
        super.initialize(stateManager, app);
        this.app = (Application) app;
        this.guiNode = this.app.getGuiNode();

        // create background
        Quad quad = new Quad(400, 600);
        background = new Geometry("MenuBackground", quad);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", new ColorRGBA(0, 0, 0, 0.5f)); // Semi-transparent black
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        background.setMaterial(mat);
        background.setLocalTranslation(250, app.getContext().getSettings().getHeight() - 450, 0);

        // create text
        text = new BitmapText(this.app.getGuiFont());
        text.setText("Menu [Press Key]:\nFurniture:\n 1: Couch\n 2: Table\n 3: Shelf\n\nTexture:\n c: WHITE\n v: GREEN\n b: RED\n n: BLUE\n m: YELLOW\n f: STONE\n\n[Press TAB to close]");
        text.setSize(this.app.getGuiFont().getCharSet().getRenderedSize());
        text.setLocalTranslation(300, app.getContext().getSettings().getHeight(), 0);

        hideMenu();
    }

    public boolean visible() {
        return this.isVisible;
    }

    public void toggleMenu() {
        isVisible = !isVisible;
        if (isVisible) {
            showMenu();
        } else {
            hideMenu();
        }
    }

    private void showMenu() {
        guiNode.attachChild(background);
        guiNode.attachChild(text);
    }

    private void hideMenu() {
        guiNode.detachChild(text);
        guiNode.detachChild(background);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        guiNode.detachChild(text);
    }
}
