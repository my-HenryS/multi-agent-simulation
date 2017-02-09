package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.ModelShape;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SVSR_SafetyRegion;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Whatever on 2016/10/22.
 */
public class StraightPathFinder implements PathFinder {
    LinkedList<Point> goals = new LinkedList<>();
    ModelShape agentModelShape;
    Scene scene;

    public StraightPathFinder(Scene targetScene, ModelShape agentModelShape) {
        for(Iterator<SceneValue> iterator = scene.getValueSet().iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if(sceneValue instanceof SVSR_SafetyRegion){
                goals.addLast(((SVSR_SafetyRegion)sceneValue).getValue().getModelShape().getReferencePoint().clone()) ;
            }
        }
        this.agentModelShape = agentModelShape.clone();
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
