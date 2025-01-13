package org.example;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Couch implements Furniture {

    private final Spatial model;

    public Couch(AssetManager assetManager) {
        model = assetManager.loadModel("objects/couch.obj");
    }

    @Override
    public Spatial getModel() {
        return this.model;
    }

    @Override
    public float getDefaultScale() {
        return 0.01f;
    }

    @Override
    public Vector3f getCompensationVector() {
        return new Vector3f(0, -4, 0);
    }

}
