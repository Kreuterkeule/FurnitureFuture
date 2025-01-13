package org.example;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.PhysicsRayTestResult;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.math.Ray;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PreviewHandler {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Node rootNode;
    private final BulletAppState bulletAppState;
    private Spatial previewObject;        // Initialize the transparent material for preview

    private Vector3f compensationVector;
    private final Material transparentMaterial;
    private boolean previewing = false;
    private final Application app;

    public PreviewHandler(Application app) {
        AssetManager assetManager = app.getAssetManager();
        this.rootNode = app.getRootNode();
        this.bulletAppState = app.getBulletAppState();
        this.app = app;

        transparentMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        transparentMaterial.setColor("Color", new ColorRGBA(0, 255, 0, .5f));
        transparentMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
    }

    public void setPreviewObject(Spatial object, float scale, Vector3f compensationVector) {
        if (previewObject != null) {
            rootNode.detachChild(previewObject);
        }
        this.compensationVector = compensationVector;
        executor.submit(() -> {

            previewObject = object.clone();
            previewObject.setLocalScale(scale);

            previewObject.setMaterial(transparentMaterial);
            app.enqueue(() -> {
                rootNode.attachChild(previewObject);
            });
        });
    }

    public void moveObjectWithRayCasting(Vector3f origin, Vector3f direction) {
        if (previewObject == null) return;

        Ray ray = new Ray(origin, direction);
        List<PhysicsRayTestResult> results = app.getBulletAppState().getPhysicsSpace().rayTest(ray.getOrigin(), ray.getDirection().mult(100));

        if (!results.isEmpty()) {
            for (PhysicsRayTestResult result : results) {
                if (result.getCollisionObject().getCollisionGroup() == PhysicsCollisionObject.COLLISION_GROUP_01) { // check if it isn't the preview
                    /* calculating the contact point of the ray and the object */
                    Vector3f contactPoint = ray.getOrigin().add(ray.getDirection().mult(result.getHitFraction() * 100));
                    previewObject.setLocalTranslation(contactPoint.add(compensationVector));
                    previewObject.setCullHint(Spatial.CullHint.Inherit); /* reset the visibility */
                    break;
                }
            }
        } else {
            /* Hide the preview object if no collision point is found (like in the sky) */
            previewObject.setCullHint(Spatial.CullHint.Always);
        }
    }

    public void placeObject(Material material) {
        if (previewObject == null) return;

        // pre copy local variables so that they won't be overwritten by cancelPreview or even a new setPreviewObject()
        Spatial placedObject = previewObject.clone();

        executor.submit(() -> {
            placedObject.setMaterial(material);

            RigidBodyControl objectPhys = new RigidBodyControl(1f); // Static object
            objectPhys.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_01);
            placedObject.addControl(objectPhys);
            placedObject.setUserData("removable", "yes");
            bulletAppState.getPhysicsSpace().add(objectPhys);

            app.enqueue(() -> {
                rootNode.attachChild(placedObject);
            });
        });

        this.cancelPreview();
    }

    public void rotatePreview(float angle) {
        if (previewObject != null) {
            this.previewObject.rotate(0, angle, 0);
        }
    }

    public void cancelPreview() {
        if (previewObject != null) {
            rootNode.detachChild(previewObject);
            previewObject = null;
            this.previewing = false;
        }
    }

    public void toggle() {
        this.previewing = !this.previewing;
    }

    public boolean isPreviewing() {
        return this.previewing;
    }
}
