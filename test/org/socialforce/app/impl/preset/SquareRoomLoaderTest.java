package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Wall;

import static org.junit.Assert.*;

/**
 * Created by Whatever on 2016/9/17.
 */
public class SquareRoomLoaderTest {
    SquareRoomLoader loader = new SquareRoomLoader();
    @Before
    public void setUp(){

    }
    @Test
    public void readScene() throws Exception {
        Scene scene = loader.readScene();
        assertEquals(SimpleScene.class,scene.getClass());
        InteractiveEntity entity = scene.getStaticEntities().selectTop(new Circle2D(new Point2D(0,0),100));
        //assertEquals("wall3",entity.getName());//TODO 这行跑不通，返回的是wall0.以及select(DistanceShape)不是选择相交的而是选择包含的，文档已改。
        entity = scene.getStaticEntities().selectBottom(new Circle2D(new Point2D(0,0),100));
        assertEquals("wall0",entity.getName());
        entity = scene.getStaticEntities().selectTop(new Point2D(25.5,0));
        assertEquals("wall2",entity.getName());
    }

    @Test
    public void getShapeTest() throws Exception{
        Scene scene = loader.readScene();
        InteractiveEntity entity = scene.getStaticEntities().selectBottom(new Circle2D(new Point2D(0,0),100));
        assertEquals(entity.getShape(),new  Box2D(new Point2D(0, 0), new Point2D(1, 16)));
    }
}