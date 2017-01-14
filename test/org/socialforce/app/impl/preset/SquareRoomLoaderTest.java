package org.socialforce.app.impl.preset;

import org.junit.Before;
import org.junit.Test;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.impl.SimpleScene;
import org.socialforce.app.impl.StandardSceneLoader;
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
    SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                                                 new Wall[]{
                                                        new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                                                        new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                                                        new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                                                        new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
                                                 });
    @Before
    public void setUp(){

    }
    @Test
    public void readScene() throws Exception {
        Scene scene = loader.staticScene();
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
        Scene scene = loader.staticScene();
        InteractiveEntity entity = scene.getStaticEntities().selectBottom(new Circle2D(new Point2D(0,0),100));
        assertEquals(entity.getShape(),new  Box2D(new Point2D(0, 0), new Point2D(1, 16)));
    }
}