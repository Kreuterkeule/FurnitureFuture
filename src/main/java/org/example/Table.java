package org.example;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Table implements Furniture {

    private final Spatial model;

    public Table(AssetManager assetManager) {
        this.model = assetManager.loadModel("objects/table2.obj");
        this.model.rotate((float) Math.toRadians(-90), 0, 0);
    }

    @Override
    public Spatial getModel() {
        return this.model;
    }

    @Override
    public float getDefaultScale() {
        return .2f;
    }

    @Override
    public Vector3f getCompensationVector() {
        return new Vector3f(0, -3f, 0);
    }
}
