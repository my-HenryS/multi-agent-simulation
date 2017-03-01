package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Rectangle2D;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Door;
import org.socialforce.model.impl.Monitor;
import org.socialforce.model.impl.Wall;
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
 * Created by Whatever on 2017/3/1.
 */
public class ApplicationForDoorTest extends SimpleApplication implements SocialForceApplication {
    DistanceShape template;
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            Scene scene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(scene, template);
            GoalStrategy strategy = new NearestGoalStrategy(scene, pathFinder);
            strategy.pathDecision();
            while (!scene.getAllAgents().isEmpty()) {
                scene.stepNext();
            }
        }
    }



    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("standard.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(4,-2,2,5)})));
        parameters.addLast(genParameter(new SV_RandomAgentGenerator(100,new Box2D(0,0,10,-10),template)));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(1,4,8,1))));
        parameters.addLast(genParameter(new SV_Door(new Rectangle2D(new Point2D(4.5,0.25),1,0.5,0),new Point2D(4,0),new double[]{0,Math.PI/2})));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

}


