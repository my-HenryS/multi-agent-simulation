package org.socialforce.strategy.impl;

import org.socialforce.scene.Scene;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.scene.SceneValue;
import org.socialforce.scene.impl.SVSR_SafetyRegion;
import org.socialforce.strategy.Path;
import org.socialforce.strategy.PathFinder;
import org.socialforce.model.impl.SafetyRegion;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by sunjh1999 on 2016/10/21.
 * TODO 性能优化 需结合scene重构
 */
public class AStarPathFinder implements PathFinder {
    double min_div = 0.3;
    private double map[][];
    double distance[][];
    Point previous[][];
    Shape agentShape;
    Scene scene;
    double delta_x = 0;        //偏移量x
    double delta_y = 0;        //偏移量y
    LinkedList<Maps> mapSet = new LinkedList<>();
    LinkedList<Point> goals = new LinkedList<>();

    protected class Maps{
        private double map[][];
        private double distance[][];
        private Point previous[][];
        private Point goal;
        private double delta_x;
        private double delta_y;

        public Maps(){}
        public Maps(double map[][],double distance[][],Point previous[][],Point goal,double delta_x,double delta_y){
            this.map = map;
            this.distance = distance;
            this.previous = previous;
            this.goal = goal;
            this.delta_x = delta_x;
            this.delta_y = delta_y;
        }

        public Point findNext(Point start_point){
            double x = (start_point.getX() - delta_x ) / min_div;
            double y = (start_point.getY() - delta_y ) / min_div;
            double distance = Double.POSITIVE_INFINITY;
            int tempX = 0, tempY = 0;
            for(int i = (int)x-1; i<= x+1; i++){
                for(int j = (int)y-1; j<= y+1; j++){
                    if(map[i][j] == 0){
                        if(new Point2D(i,j).distanceTo(new Point2D(x,y)) < distance){
                            tempX = i; tempY = j;
                            distance = new Point2D(i,j).distanceTo(new Point2D(x,y));
                        }
                    }
                }
            }
            x = tempX;
            y = tempY;
            Point next = previous[(int)x][(int)y];
            Point tobeReturn = next.clone().scaleBy(min_div).moveBy(delta_x, delta_y);
            return tobeReturn;
        }

        public Point getGoal(){
            return goal.clone().scaleBy(min_div).moveBy(delta_x, delta_y);
        }
    }
    /**
     * assign map directly
     */
    public AStarPathFinder(double[][] new_map, Agent agent, Point ... goals){
        min_div = 1;
        this.map = new_map;
        for(Point goal : goals){
            this.goals.addLast(goal.clone());
        }
        this.agentShape = agent.getShape().clone();
        for(Point goal: goals){
            maps_initiate(goal);
        }
    }

    /**

    public AStarPathFinder(Scene targetScene, Agent agent){
        this.goal = goal.clone();
        this.agentShape = agent.getShape().clone();
        this.scene = targetScene.standardclone();
        scene_standardize();
        goal_standardize();
        start_point_standardize();
        maps_initiate();
        Maps new_maps = new Maps(map,distance,previous,goal);
        mapSet.offer(new_maps);
    }* assign map with scene, agent and goal
     */

    public AStarPathFinder(Scene scene, Shape templateShape){
        this.scene = scene.standardclone();
        this.agentShape = templateShape.clone();
        scene_initiate();   //set standard scene and goals
        goals.forEach(this::maps_initiate);
    }

    private void scene_initiate(){
        scene_standardize();
        for(Iterator<SceneValue> iterator = scene.getValueSet().iterator(); iterator.hasNext();){
            SceneValue sceneValue = iterator.next();
            if(sceneValue instanceof SVSR_SafetyRegion){
                goals.addLast(goal_standardize(((SVSR_SafetyRegion)sceneValue).getValue().getShape().getReferencePoint().clone())) ;
            }
        }
    }

    private void scene_standardize() {
        delta_x = scene.getBounds().getStartPoint().getX();
        delta_y = scene.getBounds().getStartPoint().getY();
        EntityPool all_blocks = scene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(-delta_x, -delta_y));
        }
    }

    private Point goal_standardize(Point goal){
        goal.moveBy(-delta_x, -delta_y).scaleBy(1/min_div);
        return goal;
    }

    private void maps_initiate(Point goal) {
        if(agentShape == null){
            throw new IllegalStateException("No template shape applied");
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
        Maps new_maps = new Maps(map,distance,previous,goal,delta_x,delta_y);
        mapSet.offer(new_maps);
    }

    private LinkedBlockingQueue<Point> get_surroundings(Point start, Point center, LinkedBlockingQueue<Point> point_set){
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

    private boolean available(int x, int y){
        return x>=0 && y>=0 && x<map.length && y<map[0].length;
    }

/*
    Plan for a certain goal
 */

    public Path plan_for(Point goal) {
        Point destination = goal.clone();
        destination = goal_standardize(goal.clone());
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }

        if(mapSet == null){
            throw new IllegalStateException("No maps initiated");
        }

        for(Maps maps: mapSet){
            if(maps.goal.equals(destination)){
                return new AStarPath(maps);
            }
        }
        return null;
    }

    public void set_deltax(double x){
        this.delta_x = x;
    }

    public void set_deltay(double y){
        this.delta_y = y;
    }

    public Point[] getGoals(){
        Point [] points = new Point[goals.size()];
        for(int i = 0; i < points.length; i++){
            points[i] = goals.get(i).clone().scaleBy(min_div).moveBy(delta_x, delta_y);;
        }
        return points;
    }
}