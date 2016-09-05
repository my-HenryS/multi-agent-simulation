package org.socialforce.app.impl.preset;

import org.socialforce.app.ParameterSet;
import org.socialforce.app.Scene;
import org.socialforce.app.SceneLoader;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.Wall;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Whatever on 2016/8/31.
 */
public class LoadSquareRoom implements SceneLoader {

    @Override
    public void setSource(InputStream stream) {
        //矩形房间不从流中读取
    }

    @Override
    public void setSource(File file) {
        //矩形房间不从文件中读取
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
        Box2D w1,w2,w3,w4;
        w1 = new Box2D(new Point2D(0,0),new Point2D(1,16));
        w2 = new Box2D(new Point2D(1,15),new Point2D(25,16));
        w3 = new Box2D(new Point2D(25,0),new Point2D(26,16));
        w4 = new Box2D(new Point2D(1,0),new Point2D(25,1));
        squareRoomScence.setWallsByShape(0,w1);
        squareRoomScence.setWallsByShape(1,w2);
        squareRoomScence.setWallsByShape(2,w3);
        squareRoomScence.setWallsByShape(3,w4);
    }
}

