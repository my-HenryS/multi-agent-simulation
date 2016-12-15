package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Whatever on 2016/12/16.
 */
public class ECTestLoader2 implements SceneLoader {
    @Override
    public void setSource(InputStream stream) {

    }

    @Override
    public void setSource(File file) {

    }

    @Override
    public Scene readScene() {
        Scene scene = new SimpleScene(new Box2D(-50, -50, 100, 100));
        Wall[] walls = new Wall[]{
                new Wall(new Box2D(new Point2D(4, 3), new Point2D(25, 4))),
                new Wall(new Box2D(new Point2D(26.75,3), new Point2D(32,4))),
                new Wall(new Box2D(new Point2D(32, 4), new Point2D(33, 13.5))),
                new Wall(new Box2D(new Point2D(32, 14.5), new Point2D(33, 20))),
                new Wall(new Box2D(new Point2D(3, 4), new Point2D(4, 9.5))),
                new Wall(new Box2D(new Point2D(3, 10.25), new Point2D(4,20))),
                new Wall(new Box2D(new Point2D(4, 20), new Point2D(13, 21))),//???
                new Wall(new Box2D(new Point2D(14.5,20), new Point2D(32, 21))),
                new Wall(new Circle2D(new Point2D(11.5,11),2)),
                new Wall(new Circle2D(new Point2D(26,11.5),2))
        };
        for (int i = 0;i<walls.length;i++) {
            walls[i].setName("wall" + i);
            scene.getStaticEntities().add(walls[i]);
            walls[i].setScene(scene);
            walls[i].setModel(new SimpleSocialForceModel());
        }

        return scene;
    }

    @Override
    public ParameterSet readParameterSet() {
        return null;
    }
}


