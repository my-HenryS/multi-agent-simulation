package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Monitor;
import org.socialforce.scene.ParameterPool;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.SceneValue;
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
public class ApplicationForMutidoorOverView extends SimpleApplication implements SocialForceApplication {
    DistanceShape template;

    public ApplicationForMutidoorOverView(){
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(scene, template);
            GoalStrategy strategy = new NearestGoalStrategy(scene, pathFinder);
            strategy.pathDecision();
            while (!scene.getAllAgents().isEmpty()) {
                scene.stepNext();
            }
            for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(Monitor.class).iterator(); iter.hasNext();){
                Monitor monitor = (Monitor)iter.next();
                System.out.println(monitor.sayVelocity());
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
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("mutidoor.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        for(int i = 0;i<10;i++){
            parameters.addLast(genParameter(new SV_RandomAgentGenerator(100,new Box2D(i*10,0,10,-10),template)));
            parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(i*10,20,9,1))));
        }
        for (int i = 0;i<3;i++){
            parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(i*30+15-doorwidth/2,-1,doorwidth,3),new Box2D(i*30+27-doorwidth/2,-1,doorwidth,3),new Box2D(i*30+40-doorwidth,-1,doorwidth,3)})));
        }
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(45-doorwidth/2,-2.2,doorwidth,-2),new Box2D(57-doorwidth/2,-2.2,doorwidth,-2),new Box2D(70-doorwidth,-2.2,doorwidth,-2)})));
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(75-doorwidth/2,-3,doorwidth,4),new Box2D(87-doorwidth/2,-3,doorwidth,4),new Box2D(100-doorwidth,-3,doorwidth,4)})));
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(5-doorwidth,-1,doorwidth*2,3)})));
        for (int i = 0; i< 250;i++){
            for (int j = 0; j< 25;j++){
                parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(0.4*i,-0.4*j),0.2))));
            }
        }
        for (int i = 0; i < 20;i++){
            for (int j=0;j<250;j++) {
                double temp = j*0.4;
                if (temp<3.5||(temp>6.5&&temp<14.25)||(temp>15.75&&temp<26.25)||(temp>27.75&&temp<38.5)
                        ||(temp<40&&temp>44.25)||(temp>45.75&&temp<56.25)||(temp>57.75&&temp<68.5)
                        ||(temp<70&&temp>74.25)||(temp>75.75&&temp<86.25)||(temp>87.75&&temp<101)){continue;}
                parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(temp, i * 0.4), 0.2))));
            }
        }
        //parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(10,0),0.2))));
        //parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(10,-1),0.2))));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

}