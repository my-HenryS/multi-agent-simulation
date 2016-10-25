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
import java.util.concurrent.LinkedBlockingQueue;


/**
 * Created by sunjh1999 on 2016/10/21.
 */
public class AStarPathFinder implements PathFinder {
    static final int min_div = 1;

    @Override
    public Path plan(Scene targetScene, Agent agent, Point goal) {
        AStarPath path = new AStarPath();
        double map[][];
        Point start_point = new Point2D((int) agent.getShape().getReferencePoint().getX(), (int) agent.getShape().getReferencePoint().getY());
        EntityPool all_blocks = targetScene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            assert (!entity.getShape().contains(start_point));

        }
        map = map_initiate(targetScene, agent, goal);
        path = plan_for(start_point, map, goal);
        return null;
    }

    public double[][] map_initiate(Scene targetScene, Agent agent, Point goal) {
        double x_range = targetScene.getBounds().getEndPoint().getX() - targetScene.getBounds().getStartPoint().getX(),
                y_range = targetScene.getBounds().getEndPoint().getY() - targetScene.getBounds().getStartPoint().getY();
        double[][] map = new double[(int) x_range / min_div][(int) y_range / min_div];
        EntityPool all_blocks = targetScene.getStaticEntities();
        Shape agent_shape = agent.getShape();
        for (int i = 0; i < (int) x_range / min_div; i++) {
            for (int j = 0; j < (int) y_range / min_div; j++) {
                map[i][j] = 0;
                for (InteractiveEntity entity : all_blocks) {
                    agent_shape.moveTo(new Point2D(i, j));
                    //assert( == entity.getShape().getClass());
                    if (agent_shape.hits((Box) entity.getShape())) {
                        map[i][j] = 1;
                    } else if (i < goal.getX() && goal.getX() < i + 1 && j < goal.getY() && goal.getY() <= i + 1) {
                        map[i][j] = 2;        //暂定2为目标点，目标点为goalx,y坐标的向下取整
                    } else {
                        map[i][j] = 0;        //暂定1为有障碍物占领
                    }
                }
            }
        }
        return map;
    }


    public AStarPath plan_for(Point start_point, double[][]map, Point goal) {
        double distance[][] = new double[map.length][map[0].length];
        LinkedBlockingQueue<Point> points = new LinkedBlockingQueue<Point>();
        points.offer(start_point);
        Point curr_point = start_point;
        while(curr_point != null ){
            curr_point = points.poll();

        }
        return null;
    }

    public LinkedBlockingQueue<Point> get_surroundings(Point center, double[][] distance, double[][]map, LinkedBlockingQueue<Point> point_set){
        int x = (int)center.getX();
        int y = (int)center.getY();
        for(int i = x-1; i <= x+1; i++){
            for(int j = y-1; j<=y+1; j++){
                if(i==x && j==y) continue;
                Point tmp_point = new Point2D(i,j);
                if(map[i][j]==0 && (distance[i][j]==0 || distance[i][j] > distance[x][y]+center.distanceTo(tmp_point) )){
                    distance[x-1][y-1] = distance[x][y]+Math.sqrt(2);
                    point_set.add(tmp_point);
                }

            }
        }


        return point_set;

    }
}