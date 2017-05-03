package org.socialforce.strategy.impl;

import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.scene.Scene;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
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
        this.agentShape = agentShape.clone();
        this.scene = targetScene;
        for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(SafetyRegion.class).iterator(); iter.hasNext();){
            SafetyRegion safetyRegion = (SafetyRegion)iter.next();
            goals.addLast(safetyRegion.getShape().getReferencePoint().clone()) ;
        }
    }

    /**
     * 从起点到目标点之间产生一条路径。
     * @return 搜索出的路径。
     */
    @Override
    public Path plan_for(Point goal) {
        return new StraightPath(goal);
    }

    public Path constraint_plan_for(Point goal, Point ... toBeContained) {
        Point [] pathPoints = new Point[toBeContained.length+1];
        for(int i = 0;i < toBeContained.length;i++){
            pathPoints[i] = toBeContained[i];
        }
        pathPoints[-1] = goal;
        return new StraightPath(pathPoints);
    }

    @Override
    public Point[] getGoals() {
        Point [] points = new Point[goals.size()];
        for(int i = 0; i < points.length; i++){
            points[i] = goals.get(i);
        }
        return points;
    }

    public void addSituation(Scene scene, Point goal){
        this.scene = scene;
        goals.addLast(goal);
    }

    public void addSituation(Point goal){
        goals.addLast(goal);
    }

    public void clearCache(){
        goals.clear();
    }

}
