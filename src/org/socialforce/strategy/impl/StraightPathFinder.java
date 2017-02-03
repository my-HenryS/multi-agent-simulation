package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SV_SafetyRegion;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder {
    LinkedList<Point> goals = new LinkedList<>();
    Shape agentShape;
    Scene scene;

    public StraightPathFinder(Scene targetScene, Shape agentShape) {
        for(Iterator<SceneValue> iterator = scene.getValueSet().iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if(sceneValue instanceof SV_SafetyRegion){
                goals.addLast(((SV_SafetyRegion)sceneValue).getValue().getShape().getReferencePoint().clone()) ;
            }
        }
        this.agentShape = agentShape.clone();
        this.scene = targetScene;

    }

    /**
     * 从起点到目标点之间产生一条路径。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan_for(Point goal) {
        return new StraightPath(goal);
    }

    @Override
    public Point[] getGoals() {
        Point [] points = new Point[goals.size()];
        for(int i = 0; i < points.length; i++){
            points[i] = goals.get(i);
        }
        return points;
    }

}
