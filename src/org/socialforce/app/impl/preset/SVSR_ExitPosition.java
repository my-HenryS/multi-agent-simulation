package org.socialforce.app.impl.preset;

import org.socialforce.app.Scene;
import org.socialforce.app.SceneValue;
import org.socialforce.app.SimpleScene;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;

/**
 * Created by Whatever on 2016/8/31.
 */
public class SVSR_ExitPosition implements SceneValue{
    /**
     * 目前该场景只允许四个门，所以就先这样好了……
     */
    Point[] position = new Point[4];
    Box2D[] gates = new Box2D[4];
    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName() {

    }

    @Override
    public Comparable getValue() {
        return null;
    }

    @Override
    public void apply(Scene scene) {
        if (scene instanceof SimpleScene){
            for (int i = 0;i < 4;i++){
            ((SimpleScene) scene).setGates(i,gates[i]);}
        }
        else throw new IllegalArgumentException("此方法只能用于设置矩形房间");
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public void setPosition(int num,Point point){
        position[num]=point;
        gates[num] = new Box2D(new Point2D(0,0),new Point2D(2,2));
        gates[num].moveTo(point);
    }
}
