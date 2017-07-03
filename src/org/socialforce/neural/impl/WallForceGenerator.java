package org.socialforce.neural.impl;

import org.socialforce.geom.Point;
import org.socialforce.geom.Vector;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Vector2D;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.Scene;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.impl.AStarPathFinder;

import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public class WallForceGenerator extends ForceGenerator{
    AStarPathFinder pathFinder;
    Scene scene; //场景
    int[][] map;
    double min_div, dX, dY;
    double range = 1; //前后左右各2m
    Path path; //A星场

    public WallForceGenerator(double timestep, int intercept, double min_div) {
        super(timestep, intercept);
        this.min_div = min_div;
    }

    public Vector2D calAcc(Vector2D a, Vector2D b){
        return new Vector2D((a.getX()-b.getX())/timestep,(a.getY()-b.getY())/timestep);
    }

    /**
     * 门前有柱子
     */
    protected void setMap(Scene scene){
        pathFinder = new AStarPathFinder(scene,new Circle2D(new Point2D(0,0),0.001),min_div);
        Point goal = scene.getStaticEntities().selectClass(SafetyRegion.class).iterator().next().getShape().getReferencePoint();
        path = pathFinder.plan_for(goal);
        map = pathFinder.getMap();
        dX = pathFinder.get_deltax();
        dY = pathFinder.get_deltay();
    }

    public double[] getSurrounding(Point2D c){
        int x = (int)((c.getX() - dX)/min_div), y = (int)((c.getY() - dY)/min_div);  //计算坐标c在map中的坐标
        int range = (int)(this.range / min_div);   //计算map中的真实范围
        //计算影响范围
        int leftbound = x - range >= 0 ? x - range : 0;
        int rightbound = x + range <=  map.length -1 ? x + range : map.length - 1;
        int upbound = y + range <= map[0].length -1 ? y + range : map[0].length - 1;
        int btmbound = y - range >= 0 ? y - range : 0;
        double[] surroundings = new double[(rightbound - leftbound + 1)*(upbound - btmbound + 1)];

        for(int i = leftbound; i <= rightbound; i++){
            for(int j = btmbound; j <= upbound; j++){
                surroundings[(i - leftbound)*(upbound - btmbound + 1) + (j - btmbound)] = map[i][j];
            }
        }
        return surroundings;
    }

    public void genOutput(Scene scene) {
        setMap(scene);
        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (available(i, j)) {
                    LinkedList<Double> tempA = new LinkedList<>();
                    double[] surroundings = getSurrounding(matrix.get(i).get(j));
                    for(int t = 0; t < surroundings.length; t++){
                        tempA.add(surroundings[t]);
                    }
                    outputs.add(tempA.toArray(new Double[tempA.size()]));
                }
            }
        }

    }
}
