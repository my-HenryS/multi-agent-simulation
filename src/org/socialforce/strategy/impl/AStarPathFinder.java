package org.socialforce.strategy.impl;

import org.socialforce.model.Blockable;
import org.socialforce.model.impl.SafetyRegion;
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by sunjh1999 on 2016/10/21.
 * TODO 性能优化 需结合scene重构
 */
public class AStarPathFinder implements PathFinder {
    private double min_div = 0.2;
    private int map[][];
    private double distance[][];
    private Point previous[][];
    private Shape agentShape;
    private Scene templateScene;
    private double delta_x = 0;        //偏移量x
    private double delta_y = 0;        //偏移量y
    private LinkedList<Maps> mapSet = new LinkedList<>();
    private LinkedList<Point> goals = new LinkedList<>();

    protected class Maps{
        private int map[][];
        private double distance[][];
        private Point previous[][];
        private Point goal;
        private double delta_x;
        private double delta_y;

        public Maps(){}
        public Maps(int map[][],double distance[][],Point previous[][],Point goal,double delta_x,double delta_y){
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
            int tempX = 0, tempY = 0, available = 0, offset = 0;
            while(available == 0){
                for(int i = (int)x-offset; i<= x+offset+1; i++){
                    for(int j = (int)y-offset; j<= y+offset+1; j++){
                        if(available(i,j) && previous[i][j] != null){
                            if(new Point2D(i,j).distanceTo(new Point2D(x,y)) < distance){
                                tempX = i; tempY = j;
                                distance = new Point2D(i,j).distanceTo(new Point2D(x,y));
                                available = 1;
                            }
                        }
                    }
                }
                offset++;
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

        public double getDistance(Point current){
            double x = (current.getX() - delta_x ) / min_div;
            double y = (current.getY() - delta_y ) / min_div;
            double Distance = Double.POSITIVE_INFINITY;
            int tempX = 0, tempY = 0, available = 0, offset = 0;
            while(available == 0){
                for(int i = (int)x-offset; i<= x+offset+1; i++){
                    for(int j = (int)y-offset; j<= y+offset+1; j++){
                        if(available(i,j) && previous[i][j] != null){
                            if(new Point2D(i,j).distanceTo(goal) < Distance){
                                tempX = i; tempY = j;
                                Distance = new Point2D(i,j).distanceTo(goal);
                                available = 1;
                            }
                        }
                    }
                }
                offset++;
            }
            x = tempX;
            y = tempY;
            return distance[(int)x][(int)y]*min_div;
        }

        public boolean hasPrevious(Point start_point){
            double x = (start_point.getX() - delta_x ) / min_div;
            double y = (start_point.getY() - delta_y ) / min_div;
            for(double i = x; i<=x+1; i++){
                for(double j = y; j<=y+1; j++){
                    if(available((int)i,(int)j) && previous[(int)i][(int)j] != null) return true;
                }
            }
            return false;
        }
    }
    /**
     * assign map directly
     */
    public AStarPathFinder(int[][] new_map, Agent agent, Point ... goals){
        min_div = 1;
        this.map = new_map;
        for(Point goal : goals){
            this.goals.addLast(goal.clone());
        }
        this.agentShape = agent.getShape().clone();
        for(Point goal: goals){
            astar(goal);
        }
    }

    /**

    public AStarPathFinder(Scene targetScene, Agent agent){
        this.goal = goal.clone();
        this.agentShape = agent.getShape().clone();
        this.templateScene = targetScene.standardclone();
        scene_standardize();
        point_generate();
        start_point_standardize();
        maps_initiate();
        Maps new_maps = new Maps(map,distance,previous,goal);
        mapSet.offer(new_maps);
    }* assign map with templateScene, agent and goal
     */

    public AStarPathFinder(Scene scene, Shape templateShape){
        this.templateScene = scene.standardclone();
        this.agentShape = templateShape.clone();
        scene_initiate();   //set standard templateScene and goals
        map_initiate();
        map_generate(templateScene);
        goals.forEach(this::astar);
    }

    public AStarPathFinder(Scene scene, Shape templateShape, double min_div){
        this.min_div = min_div;
        this.templateScene = scene.standardclone();
        this.agentShape = templateShape.clone();
        scene_initiate();   //set standard templateScene and goals
        map_initiate();
        map_generate(templateScene);
        goals.forEach(this::astar);
    }

    public void clearCache(){
        mapSet.clear();
        goals.clear();
    }

    public void addSituation(Scene scene, Point goal){
        assert(scene.getBounds().equals(templateScene.getBounds()));
        Scene newScene = scene.standardclone();
        scene_generate(newScene);
        map_generate(newScene);
        goals.addLast(point_generate(goal.clone()));
        astar(goals.getLast());
    }

    public void addSituation(Point goal){
        goals.addLast(point_generate(goal.clone()));
        astar(goals.getLast());
    }

    private void scene_initiate(){
        delta_x = templateScene.getBounds().getStartPoint().getX();
        delta_y = templateScene.getBounds().getStartPoint().getY();
        for(Iterator<InteractiveEntity> iter = templateScene.getStaticEntities().selectClass(SafetyRegion.class).iterator(); iter.hasNext();){
            SafetyRegion safetyRegion = (SafetyRegion)iter.next();
            goals.addLast(point_generate(safetyRegion.getShape().getReferencePoint().clone()));
        }
        scene_generate(templateScene);
    }

    private void scene_generate(Scene scene) {
        EntityPool all_blocks = scene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(-delta_x, -delta_y));
        }
    }

    private Point point_generate(Point point){
        point.moveBy(-delta_x, -delta_y).scaleBy(1/min_div);
        return point;
    }

    private void map_initiate(){
        if(agentShape == null){
            throw new IllegalStateException("No template shape applied");
        }
        double x_range = templateScene.getBounds().getEndPoint().getX() - templateScene.getBounds().getStartPoint().getX(),
                y_range = templateScene.getBounds().getEndPoint().getY() - templateScene.getBounds().getStartPoint().getY();
        map = new int[(int)(x_range / min_div)][(int) (y_range / min_div)];
    }

    private void map_generate(Scene scene){
        EntityPool all_blocks = scene.getStaticEntities();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 0;
                for (InteractiveEntity entity : all_blocks) {
                    agentShape.moveTo(new Point2D(i*min_div, j*min_div));
                    //assert( == entity.getShape().getClass());
                    if ((entity instanceof Blockable) && ((DistanceShape)agentShape).distanceTo(entity.getShape()) < 0) {
                        map[i][j] = 1;
                        break;
                    }
                }
            }
        }

    }

    private void astar(Point goal) {
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
        Maps new_maps = new Maps(map.clone(),distance,previous,goal,delta_x,delta_y);
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
        Point destination;
        destination = point_generate(goal.clone());
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }

        if(mapSet == null){
            throw new IllegalStateException("No maps initiated");
        }

        for(Maps maps: mapSet){
            if(maps.goal.epsilonEquals(destination, 1e-10)){
                return new AStarPath(maps);
            }
        }
        return null;
    }

    public Path constraint_plan_for(Point goal, Point ... toBeContained) {
        Point destination;
        destination = point_generate(goal.clone());
        if(agentShape == null){
            throw new IllegalStateException("No agent applied");
        }

        if(mapSet == null){
            throw new IllegalStateException("No maps initiated");
        }

        for(Maps maps: mapSet){
            if(maps.goal.epsilonEquals(destination, 1e-10)){
                boolean succ = true;
                for(Point contain:toBeContained){
                    if(!maps.hasPrevious(contain)){
                        succ = false;
                        break;
                    }
                }
                if(succ) return new AStarPath(maps);
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

    public double get_deltax(){
        return delta_x;
    }

    public double get_deltay(){
        return delta_y;
    }

    public double get_minDiv(){
        return min_div;
    }

    public Point[] getGoals(){
        Point [] points = new Point[goals.size()];
        for(int i = 0; i < points.length; i++){
            points[i] = goals.get(i).clone().scaleBy(min_div).moveBy(delta_x, delta_y);;
        }
        return points;
    }

    /**
     * 获取场景地图
     */
    public int[][] getMap(){
        return map;
    }
}