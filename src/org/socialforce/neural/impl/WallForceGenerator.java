package org.socialforce.neural.impl;

import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.neural.Coordinates;
import org.socialforce.neural.DataSetGenerator;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.impl.AStarPathFinder;

import java.io.InputStream;
import java.util.LinkedList;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by sunjh1999 on 2017/3/27.
 */
public class WallForceGenerator extends ForceGenerator{
    DistanceShape template = new Circle2D(new Point2D(0,0),0.486/2);
    LinkedList<Scene>scenes = new LinkedList<>();
    int[][] map;
    double min_div, dX, dY;
    int range = 2; //前后左右各2m

    /**
     * 门前有柱子
     */
    protected void setMap(){
        double DoorWidth = 1.36;
        int density = 10;

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-DoorWidth/2,-0.5,DoorWidth,2)})));
        parameters.addLast(genParameter(new SV_RandomAgentGenerator(density*10,new Box2D(0,-10,10,5),template)));
        parameters.addLast(genParameter(new SV_Wall(new Box2D[]{new Box2D(4,-4,2,2)}),new SV_Wall(new Shape[]{new Circle2D(new Point2D(5,-3),1)})));
        parameters.addLast(genParameter(new SV_Monitor(new Box2D(0,0,10,1))));
        loader.readParameterSet(parameters);
        scenes.addAll(loader.readScene());
        AStarPathFinder pathFinder = new AStarPathFinder(scenes.get(0),new Circle2D(new Point2D(0,0),0.001));
        map = pathFinder.getMap();
        min_div = pathFinder.get_minDiv();
        dX = pathFinder.get_deltax();
        dY = pathFinder.get_deltay();
    }

    public double[] getSurrounding(Coordinates c){
        int x = (int)((c.X() - dX)/min_div), y = (int)((c.Y() - dY)/min_div);  //计算坐标c在map中的坐标
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

    @Override
    public void genOutput() {
        setMap();
        for (int i = 0 ; i < matrix.size() ; i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (available(i, j)) {
                    double[] surroundings = getSurrounding(matrix.get(i).get(j));
                    outputs.add(surroundings);
                }
            }
        }

    }
}
