package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.impl.SafetyRegion;

import java.util.LinkedList;
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
    LinkedList<Maps> mapset = new LinkedList<>();

    private class Maps{
        double map[][];
        double distance[][];
        Point previous[][];
        Point goal;

        public Maps(){}
        public Maps(double map[][],double distance[][],Point previous[][], Point goal){
            this.map = map;
            this.distance = distance;
            this.previous = previous;
            this.goal = goal;
        }
    }
    /**
     * assign map directly
     */
    public AStarPathFinder(double[][] new_map, Agent agent, Point goal){
        this.map = new_map;
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
        maps_initiate();
        Maps new_maps = new Maps(map,distance,previous,goal);
        mapset.offer(new_maps);
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
        Maps new_maps = new Maps(map,distance,previous,goal);
        mapset.offer(new_maps);
    }

    public AStarPathFinder(Scene targetScene){
        this.scene = targetScene;
        scene_standardize();
    }

    public void applyGoal(Point goal){
        this.goal = goal.clone();
        goal_standardize();
        Maps curr_maps = null;
        for(Maps maps:mapset){
            if(maps.goal.equals(this.goal)){
                curr_maps = maps;
                break;
            }
        }
        if(curr_maps != null){
            map = curr_maps.map;
            distance = curr_maps.distance;
            previous = curr_maps.previous;
        }
        else{
            maps_initiate();
        }
        if(map[(int)start_point.getX()][(int)start_point.getY()] == 1){
            int x = (int)start_point.getX();
            int y = (int)start_point.getY();
            for(int i = x-1; i <= x+1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if(map[i][j]==0) start_point.moveTo(i,j);
                }
            }
        }
    }

    public void applyAgent(Agent agent){
        this.agentShape = agent.getShape().clone();
        start_point_standardize();
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
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }
        if(map == null){
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
        }
        distance = new double[map.length][map[0].length];
        previous = new Point[map.length][map[0].length];
        LinkedBlockingQueue<Point> points = new LinkedBlockingQueue<Point>();
        points.offer(goal);
        Point curr_point = points.poll();
        previous[(int)goal.getX()][(int)goal.getY()] = goal;
        while(curr_point != null ){
            points = get_surroundings(goal, curr_point ,points);
            curr_point = points.poll();
        }
        Maps new_maps = new Maps(map,distance,previous,goal);
        mapset.offer(new_maps);
    }


    public Path plan_for() {
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }
        Point curr_point;
        LinkedBlockingQueue<Point> points = new LinkedBlockingQueue<Point>();
        curr_point = start_point.clone();
        points.offer(curr_point);
        while(!curr_point.equals(goal)){
            curr_point = previous[(int)curr_point.getX()][(int)curr_point.getY()];
            points.offer(curr_point);
        }
        Point[] goals = new Point[points.size()];
        AStarPath path;
        if(scene!=null){
            for(int i = 0; i<goals.length; i++){
                goals[i] = points.poll().clone().scaleBy(min_div).moveBy(delta_x, delta_y);
            }
            path = new AStarPath(goals);
        }
        else{
            for(int i = 0; i<goals.length; i++){
                goals[i] = points.poll();
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