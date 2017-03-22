package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.Shape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Monitor;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.InputStream;
import java.util.Iterator;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by Administrator on 2017/2/3.
 */
public class ApplicationForMutidoor extends SimpleApplication implements SocialForceApplication {
    DistanceShape template;

    public ApplicationForMutidoor(){
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            double samplewidth = 0.4;
            double [][] matrixV = new double[(int)(20/samplewidth)][(int)(10/samplewidth)];
            currentScene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(currentScene, template);
            GoalStrategy strategy = new NearestGoalStrategy(currentScene, pathFinder);
            strategy.pathDecision();
            while (!toSkip()) {
                this.StepNext(currentScene);
            }
            onStop();
            for(Iterator<InteractiveEntity> iter = currentScene.getStaticEntities().selectClass(Monitor.class).iterator(); iter.hasNext();){
                Monitor m = (Monitor)iter.next();
                Point p = m.getShape().getReferencePoint();
                matrixV[(int)Math.rint((p.getY()+10)/samplewidth)][(int)Math.rint(p.getX()/samplewidth)] = m.sayVelocity();
            }
            for(double [] m1:matrixV){
                for(double m:m1){
                    System.out.print(String.format("%.1f", m)+"\t");
                }
                System.out.println();
            }
        }
    }


    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        double doorwidth = 1.5;
        double samplewidth = 0.4;
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
            parameters.addLast(genParameter(new SV_RandomAgentGenerator(100,new Box2D(0,0,10,-10),template)));
            parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(0,10,10,1))));
            for (int i = 0; i< 10/samplewidth;i++){
                for (int j = 0; j < 20/samplewidth; j++){
                    parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(i*samplewidth,j*samplewidth-10),samplewidth/2))));
                }
            }
            parameters.addLast(genParameter(new SV_Wall(new Shape[]{new Box2D(0,0,5-doorwidth/2,1),new Box2D(5+doorwidth/2,0,5-doorwidth/2,1),new Box2D(0,-3,5-doorwidth/2,-0.5),new Box2D(5+doorwidth/2,-3,5-doorwidth/2,-0.5)})
            ,new SV_Wall(new Shape[]{new Box2D(0,0,7-doorwidth/2,1),new Box2D(7+doorwidth/2,0,3-doorwidth/2,1),new Box2D(0,-3,7-doorwidth/2,-0.5),new Box2D(7+doorwidth/2,-3,3-doorwidth/2,-0.5)})
            ,new SV_Wall(new Shape[]{new Box2D(0,0,10-doorwidth,1),new Box2D(0,-3,10-doorwidth,-0.5)})
            ,new SV_Wall(new Shape[]{new Box2D(0,-2,7-doorwidth/2,3),new Box2D(7+doorwidth/2,-2,3-doorwidth/2,3)})
            ,new SV_Wall(new Shape[]{new Box2D(0,-2,5-doorwidth/2,3),new Box2D(5+doorwidth/2,-2,5-doorwidth/2,3)})
            ,new SV_Wall(new Shape[]{new Box2D(0,-2,10-doorwidth,3)})
            ,new SV_Wall(new Shape[]{new Box2D(0,0,5-doorwidth,1),new Box2D(5+doorwidth,0,5-doorwidth,1)})));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

}
