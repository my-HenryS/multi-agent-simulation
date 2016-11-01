package org.socialforce.model.impl;

import org.socialforce.app.Scene;
import org.socialforce.container.EntityPool;
import org.socialforce.container.impl.LinkListPool;
import org.socialforce.geom.Box;
import org.socialforce.geom.Shape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.Path;
import org.socialforce.model.PathFinder;

/**
 * Created by sunjh1999 on 2016/10/21.
 */
public class AStarPathFinder implements PathFinder {
    static final int min_div = 1;
    double map[][];
    @Override
    public Path plan(Scene targetScene, Agent agent, Point goal) {
        //AStarPath path = new AStarPath();
        Point start_point = agent.getShape().getReferencePoint();
        EntityPool all_blocks = targetScene.getStaticEntities();
        for (InteractiveEntity entity : all_blocks) {
            assert(!entity.getShape().contains(start_point));

        }
        map = map_initiate(targetScene, agent, goal);
        return null;
    }

    public double [][] map_initiate(Scene targetScene, Agent agent, Point goal){
        double x_range=targetScene.getBounds().getEndPoint().getX()-targetScene.getBounds().getStartPoint().getX(),
                y_range=targetScene.getBounds().getEndPoint().getY()-targetScene.getBounds().getStartPoint().getY();
        double[][] map = new double[(int)x_range/min_div][(int)y_range/min_div];
        EntityPool all_blocks = targetScene.getStaticEntities();
        Shape agent_shape = agent.getShape();
        for(int i = 0; i<(int)x_range/min_div; i++){
            for(int j = 0; j<(int)y_range/min_div; j++){
                map[i][j] = 0;
                for (InteractiveEntity entity : all_blocks) {
                    agent_shape.moveTo(new Point2D(i,j));
                    //assert( == entity.getShape().getClass());
                    if(agent_shape.hits((Box2D)entity.getShape())){
                        map[i][j] = 1;
                    }
                    else if(i<goal.getX() && goal.getX()<i+1 && j<goal.getY() && goal.getY()<=i+1){
                        map[i][j] = 2;        //暂定2为目标点，目标点为goalx,y坐标的向下取整
                    }
                    else{
                        map[i][j] = 0;        //暂定1为有障碍物占领
                    }
                }
            }
        }
        return map;
    }
}
