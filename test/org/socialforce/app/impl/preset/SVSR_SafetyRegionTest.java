package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.impl.SVSR_SafetyRegion;
import org.socialforce.app.impl.SimpleScene;
import org.socialforce.app.impl.StandardSceneLoader;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.Wall;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/9/17.
 */
public class SVSR_SafetyRegionTest {
    Scene scene;
    SceneLoader loader =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
            new Wall[]{
                    new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                    new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                    new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                    new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
            });
    SVSR_SafetyRegion safetyReigion = new SVSR_SafetyRegion();
    @Before
    public void SetUp(){
        scene = loader.staticScene();
        safetyReigion.setValue(new SafetyRegion(new Box2D(-4,4,2,4)));
    }


    @Test
    public void apply() throws Exception {
        InteractiveEntity entity;
        entity = scene.getStaticEntities().selectTop(new Point2D(-4,4));
        assertEquals(null,entity);
        safetyReigion.apply(scene);
        entity = scene.getStaticEntities().selectTop(new Point2D(-4,4));
        assertEquals("SafetyRegion",entity.getName());
    }

}