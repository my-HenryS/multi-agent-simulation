package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/9/17.
 */
public class SVSR_SafetyRegionTest {
    Scene scene;
    SceneLoader loader = new SquareRoomLoader();
    SVSR_SafetyRegion safetyReigion = new SVSR_SafetyRegion();
    @Before
    public void SetUp(){
        scene = loader.readScene();
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