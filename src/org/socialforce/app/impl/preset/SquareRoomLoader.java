package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
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
        Scene scene = new SimpleScene(new Box2D(-50,-50,100,100));
        return scene;
    }

    @Override
    public ParameterSet readParameterSet() {
        return null;
    }
}
