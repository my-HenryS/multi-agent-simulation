package org.socialforce.model.impl;

import org.socialforce.app.Scene;
import org.socialforce.container.EntityPool;
import org.socialforce.geom.Box;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Path;
import org.socialforce.model.PathFinder;

import java.util.LinkedList;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by sunjh1999 on 2016/10/21.
 * 测试基本补充完全
 */
public class AStarPathFinder implements PathFinder {
    static final double min_div = 0.5;
    private double map[][];
    double distance[][];
    Point previous[][];
    Point start_point;
    Point goal;
    Agent agent;
    Scene scene;
    double delta_x = 0;        //偏移量x
    double delta_y = 0;        //偏移量y
    double manhattan_distance = 0;

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
        this.goal = goal;
        this.agent = agent;
        start_point = new Point2D((int) agent.getShape().getReferencePoint().getX(), (int) agent.getShape().getReferencePoint().getY());
        this.manhattan_distance = goal.Manhattan_Distance(agent.getShape().getReferencePoint());
    }

    /**
     * assign map with scene, agent and goal
     */
    public AStarPathFinder(Scene targetScene, Agent agent, Point goal){
        this.goal = goal;
        this.agent = agent;
        this.scene = targetScene;
        delta_x = scene.getBounds().getStartPoint().getX();
        delta_y = scene.getBounds().getStartPoint().getY();
        EntityPool all_blocks = scene.getStaticEntities();
        start_point = new Point2D((int) agent.getShape().getReferencePoint().getX(), (int) agent.getShape().getReferencePoint().getY());
        for (InteractiveEntity entity : all_blocks) {
            entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(-delta_x, -delta_y));
            assert (!entity.getShape().contains(start_point));

        }
        map = map_initiate();
        distance = new double[map.length][map[0].length];
        previous = new Point[map.length][map[0].length];
        for(int i =0; i<map.length; i++){
            for(int j=0; j<map[0].length; j++){
                Point temp = new Point2D();
                previous[i][j] = temp;
            }
        }
        goal.moveBy(-delta_x, -delta_y).scaleBy(1/min_div);
        agent.getShape().getReferencePoint().moveBy(-delta_x, -delta_y);
        this.manhattan_distance = goal.Manhattan_Distance(agent.getShape().getReferencePoint());
        start_point = new Point2D((int) (agent.getShape().getReferencePoint().getX()/min_div), (int) (agent.getShape().getReferencePoint().getY()/min_div));
    }


    public double[][] map_initiate() {
        double x_range = scene.getBounds().getEndPoint().getX() - scene.getBounds().getStartPoint().getX(),
                y_range = scene.getBounds().getEndPoint().getY() - scene.getBounds().getStartPoint().getY();
        double[][] map = new double[(int)(x_range / min_div)][(int) (y_range / min_div)];
        EntityPool all_blocks = scene.getStaticEntities();
        Shape agent_shape = agent.getShape().clone();
        for (int i = 0; i < (int) (x_range / min_div); i++) {
            for (int j = 0; j < (int) (y_range / min_div); j++) {
                map[i][j] = 0;
                for (InteractiveEntity entity : all_blocks) {
                    agent_shape.moveTo(new Point2D(i*min_div, j*min_div));
                    //assert( == entity.getShape().getClass());
                    if (!(entity instanceof SafetyRegion) && agent_shape.hits((Box) entity.getShape())) {
                        map[i][j] = 1;
                    }
                }
            }
        }
        return map;
    }


    public Path plan_for() {
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
                goals[i] = point_stack.pop().scaleBy(min_div).moveBy(delta_x, delta_y);
            }
            path = new AStarPath(goals);
            send_back();
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
                if(available(i,j) && map[i][j]==0 && (distance[i][j]==0 || distance[i][j] > distance[x][y]+center.distanceTo(tmp_point )) && tmp_point.Manhattan_Distance(start)<=manhattan_distance){
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

    public void send_back(){
        EntityPool all_blocks = scene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            entity.getShape().moveTo(entity.getShape().getReferencePoint().moveBy(delta_x, delta_y));
        }
        goal.moveBy(delta_x, delta_y).scaleBy(min_div);
        agent.getShape().getReferencePoint().moveBy(delta_x, delta_y);
    }
}