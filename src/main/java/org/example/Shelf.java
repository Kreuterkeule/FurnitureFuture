package org.example;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Shelf implements Furniture {

    private final Spatial model;

    public Shelf(AssetManager assetManager) {
        model = assetManager.loadModel("objects/eb_metal_shelf_01.obj");
    }

    @Override
    public Spatial getModel() {
        return this.model;
    }

    @Override
    public float getDefaultScale() {
        return 0.08f;
    }

    @Override
    public Vector3f getCompensationVector() {
        return new Vector3f(0, -3, 0);
    }
}
