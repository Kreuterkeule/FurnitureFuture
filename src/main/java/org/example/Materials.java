package org.example;

import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public class Materials {
    private Material BLUE;
    private Material WHITE;
    private Material RED;
    private Material STONE;
    private Material YELLOW;
    private Material GREEN;
    private Material WALL;
    private Material FLOOR;
    private final AssetManager assetManager;

    public Materials(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    private Material createMaterialFromTexture(String name) {
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey textureKey = new TextureKey("Textures/" + name);
        textureKey.setGenerateMips(true);
        Texture texture = assetManager.loadTexture(textureKey);
        m.setTexture("ColorMap", texture);
        return m;
    }

    private Material createMaterialFromColor(ColorRGBA color) {
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        return m;
    }

    public Material FLOOR() {
        if (this.FLOOR == null) {
            this.FLOOR = createMaterialFromTexture("floor.png");
        }
        return this.FLOOR;
    }
    public Material WALL() {
        if (this.WALL == null) {
            this.WALL = createMaterialFromTexture("wall_carpet.png");
        }
        return this.WALL;
    }
    public Material STONE() {
        if (this.STONE == null) {
            this.STONE = createMaterialFromTexture("textures/block/stone.png");
        }
        return this.STONE;
    }

    public Material RED() {
        if (this.RED == null) {
            this.RED = createMaterialFromColor(new ColorRGBA(255,0,0,10));
        }
        return this.RED;
    }
    public Material GREEN() {
        if (this.GREEN == null) {
            this.GREEN = createMaterialFromColor(new ColorRGBA(0,255,0,10));
        }
        return this.GREEN;
    }
    public Material BLUE() {
        if (this.BLUE == null) {
            this.BLUE = createMaterialFromColor(new ColorRGBA(0,0,255,10));
        }
        return this.BLUE;
    }
    public Material WHITE() {
        if (this.WHITE == null) {
            this.WHITE = createMaterialFromColor(new ColorRGBA(255,255,255,10));
        }
        return this.WHITE;
    }

    public Material YELLOW() {
        if (this.YELLOW == null) {
            this.YELLOW = createMaterialFromColor(new ColorRGBA(0, 255,255,1));
        }
        return this.YELLOW;
    }


}
