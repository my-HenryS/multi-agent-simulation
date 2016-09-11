package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.SimpleScene;
import org.socialforce.container.impl.LinkListEntityPool;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.Wall;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Whatever on 2016/9/6.
 */
public class LoadSquareRoom implements SceneLoader {
    @Override
    public void setSource(InputStream stream) {

    }

    @Override
    public void setSource(File file) {

    }

    @Override
    public Scene readScene() {
        return null;
    }

    @Override
    public ParameterSet readParameterSet() {
        return null;
    }

    public void loadScence(SimpleScene squareRoomScence){
        Wall w1,w2,w3,w4;
        w1 = new Wall(new Box2D(new Point2D(0,0),new Point2D(1,16)));
        w2 = new Wall(new Box2D(new Point2D(1,15),new Point2D(25,16)));
        w3 = new Wall(new Box2D(new Point2D(25,0),new Point2D(26,16)));
        w4 = new Wall(new Box2D(new Point2D(1,0),new Point2D(25,1)));
        LinkListEntityPool Statics = null;
        Statics.set(0,w1);
        Statics.set(1,w2);
        Statics.set(2,w3);
        Statics.set(3,w4);
    }
}
