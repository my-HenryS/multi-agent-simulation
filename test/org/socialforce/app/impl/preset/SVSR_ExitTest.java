package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/9/17.
 */
public class SVSR_ExitTest {
    Scene scene;
    SceneLoader loader = new SquareRoomLoader();
    SVSR_Exit exit = new SVSR_Exit();              //TODO 初始化时会导致成片的空指针问题。
    @Before
    public void Setup(){
        exit.setValue(new Box2D(-1,5,3,2));
    }

    @Test
    public void ApplyTest(){
        scene = loader.readScene();
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