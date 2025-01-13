package org.example;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;


public interface Furniture {

    Spatial getModel();

    /** Used to scale the .obj to a compatible size */
    float getDefaultScale();

    /** Used to compensate for the .obj position */
    Vector3f getCompensationVector();
}
