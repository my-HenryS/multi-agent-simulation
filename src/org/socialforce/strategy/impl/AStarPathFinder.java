package org.socialforce.strategy.impl;

import org.socialforce.app.Scene;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.Box;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.impl.SafetyRegion;

import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by sunjh1999 on 2016/10/21.
 * TODO 性能优化 需结合scene重构
 */
public class AStarPathFinder implements PathFinder {
    static final double min_div = 0.5;
    private double map[][];
    double distance[][];
    Point previous[][];
    Point start_point;
    Point goal;
    Shape agentShape;
    Scene scene;
    double delta_x = 0;        //偏移量x
    double delta_y = 0;        //偏移量y

    /**
     * assign map directly
     */
    public AStarPathFinder(double[][] map, Agent agent, Point goal){
        this.map = map;
        distance = new double[map.length][map[0].length];
        previous = new Point[map.length][map[0].length];
        for(int i =0; i<map.length; i++){
            for(int j=0; j<map[0].length; j++){
                Point temp = new Point2D();
                previous[i][j] = temp;
            }
        }
        this.goal = goal.clone();
        this.agentShape = agent.getShape().clone();
        start_point = new Point2D((int) agent.getShape().getReferencePoint().getX(), (int) agent.getShape().getReferencePoint().getY());
    }

    /**
     * assign map with scene, agent and goal
     */
    public AStarPathFinder(Scene targetScene, Agent agent, Point goal){
        this.goal = goal.clone();
        this.agentShape = agent.getShape().clone();
        this.scene = targetScene;
        scene_standardize();
        goal_standardize();
        start_point_standardize();
        maps_initiate();
    }

    public AStarPathFinder(Scene targetScene){
        this.scene = targetScene;
        scene_standardize();
    }

    public void applyGoal(Point goal){
        this.goal = goal.clone();
        goal_standardize();
    }

    public void applyAgent(Agent agent){
        this.agentShape = agent.getShape().clone();
        start_point_standardize();
        maps_initiate();
    }

    public void scene_standardize() {
        delta_x = scene.getBounds().getStartPoint().getX();
        delta_y = scene.getBounds().getStartPoint().getY();
        EntityPool all_blocks = scene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(-delta_x, -delta_y));
        }
    }

    public void goal_standardize(){
        goal.moveBy(-delta_x, -delta_y).scaleBy(1/min_div);
    }

    public void start_point_standardize(){
        agentShape.getReferencePoint().moveBy(-delta_x, -delta_y);
        start_point = new Point2D((int) (agentShape.getReferencePoint().getX()/min_div), (int) (agentShape.getReferencePoint().getY()/min_div));
    }

    public void maps_initiate() {
        double x_range = scene.getBounds().getEndPoint().getX() - scene.getBounds().getStartPoint().getX(),
                y_range = scene.getBounds().getEndPoint().getY() - scene.getBounds().getStartPoint().getY();
        map = new double[(int)(x_range / min_div)][(int) (y_range / min_div)];
        EntityPool all_blocks = scene.getStaticEntities();
        for (int i = 0; i < (int) (x_range / min_div); i++) {
            for (int j = 0; j < (int) (y_range / min_div); j++) {
                map[i][j] = 0;
                for (InteractiveEntity entity : all_blocks) {
                    agentShape.moveTo(new Point2D(i*min_div, j*min_div));
                    //assert( == entity.getShape().getClass());
                    if (!(entity instanceof SafetyRegion) && ((DistanceShape)agentShape).distanceTo(entity.getShape()) < 0) {
                        map[i][j] = 1;
                    }
                }
            }
        }
        distance = new double[map.length][map[0].length];
        previous = new Point[map.length][map[0].length];
        for(int i =0; i<map.length; i++){
            for(int j=0; j<map[0].length; j++){
                Point temp = new Point2D();
                previous[i][j] = temp;
            }
        }
    }


    public Path plan_for() {
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }
        LinkedBlockingQueue<Point> points = new LinkedBlockingQueue<Point>();
        points.offer(start_point);
        Point curr_point = points.poll();
        previous[(int)start_point.getX()][(int)start_point.getY()] = start_point;
        while(curr_point != null ){
            points = get_surroundings(start_point, curr_point ,points);
            curr_point = points.poll();
        }

        Stack<Point> point_stack = new Stack<Point>();
        curr_point = goal.clone();
        point_stack.push(curr_point);
        while(!curr_point.equals(start_point)){
            curr_point = previous[(int)curr_point.getX()][(int)curr_point.getY()];
            point_stack.push(curr_point);
        }
        Point[] goals = new Point[point_stack.size()];
        AStarPath path;
        if(scene!=null){
            for(int i = 0; i<goals.length; i++){
                goals[i] = point_stack.pop().clone().scaleBy(min_div).moveBy(delta_x, delta_y);
            }
            path = new AStarPath(goals);
        }
        else{
            for(int i = 0; i<goals.length; i++){
                goals[i] = point_stack.pop();
            }
            path = new AStarPath(goals);
        }
        return path;
    }

    public LinkedBlockingQueue<Point> get_surroundings(Point start, Point center, LinkedBlockingQueue<Point> point_set){
        int x = (int)center.getX();
        int y = (int)center.getY();
        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j<=y+1; j++){
                if(i==x && j==y) continue;
                if(i==start.getX() && j==start.getY()) continue;
                Point tmp_point = new Point2D(i,j);
                if(available(i,j) && map[i][j]==0 && (distance[i][j]==0 || distance[i][j] > distance[x][y]+center.distanceTo(tmp_point) )){
                    distance[i][j] = distance[x][y]+center.distanceTo(tmp_point);
                    point_set.add(tmp_point);
                    previous[i][j] = center;
                }

            }
        }
        return point_set;

    }

    public boolean available(int x, int y){
        return x>=0 && y>=0 && x<map.length && y<map[0].length;
    }

    public void postProcessing(){
        if(scene != null){
            EntityPool all_blocks = scene.getStaticEntities();
            for (InteractiveEntity entity : all_blocks) {
                entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(delta_x, delta_y));
            }
        }
    }

    public void set_deltax(double x){
        this.delta_x = x;
    }

    public void set_deltay(double y){
        this.delta_y = y;
    }
}