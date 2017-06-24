package org.socialforce.scene.impl;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Wall;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/9/17.
 */
public class SV_ExitTest {
    Scene scene;
    SceneLoader loader =  new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
            new Wall[]{
                    new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                    new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                    new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                    new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
            });
    SV_Exit exit = new SV_Exit();
    @Before
    public void Setup(){
        exit.setValue(new Box2D[]{new Box2D(-1,5,4,2)});
    }

    @Test
    public void SetupTest(){
        assertEquals(exit.getValue()[0],new Box2D(-1,5,4,2));
    }


    @Test
    public void ApplyTest(){
        scene = loader.staticScene();
        InteractiveEntity entity,entity1;
        entity = scene.getStaticEntities().selectTop(new Point2D(5,0.5));
        entity1 = scene.getStaticEntities().selectTop(new Point2D(0.5,6));
        assertEquals("wall3",entity.getName());
        assertEquals("wall0",entity1.getName());
        exit.apply(scene);
        entity = scene.getStaticEntities().selectTop(new Point2D(5,0.5));
        entity1 = scene.getStaticEntities().selectTop(new Point2D(0.5,6));
        assertEquals("wall3",entity.getName());
        assertEquals(null,entity1);
    }
}