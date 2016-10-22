package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.Wall;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Ledenel on 2016/9/12.
 */
public class SquareRoomLoader implements SceneLoader {
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
                new Wall(new Box2D(new Point2D(0, 0), new Point2D(1, 16))),
                new Wall(new Box2D(new Point2D(1, 15), new Point2D(25, 16))),
                new Wall(new Box2D(new Point2D(25, 0), new Point2D(26, 16))),
                new Wall(new Box2D(new Point2D(1, 0), new Point2D(25, 1)))
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
