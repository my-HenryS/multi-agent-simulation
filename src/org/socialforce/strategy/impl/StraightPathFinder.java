package org.socialforce.strategy.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;
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
        for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(SafetyRegion.class).iterator(); iter.hasNext();){
            SafetyRegion safetyRegion = (SafetyRegion)iter.next();
            goals.addLast(safetyRegion.getShape().getReferencePoint().clone()) ;
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

    public Path plan_for(Point start, Point goal) {
        return new StraightPath(start, goal);
    }

    @Override
    public Point[] getGoals() {
        Point [] points = new Point[goals.size()];
        for(int i = 0; i < points.length; i++){
            points[i] = goals.get(i);
        }
        return points;
    }

    public void setScene(Scene scene, Point goal){
        this.scene = scene;
        goals.addLast(goal);
    }

    public void setScene(Scene scene, Point start, Point goal){
        this.scene = scene;
        goals.addLast(start);
        goals.addLast(goal);
    }

}
