package org.socialforce.app.Applications;

import org.socialforce.app.Application;
import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.impl.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.MultipleEntitiesGenerator;
import org.socialforce.scene.impl.RandomEntityGenerator2D;
import org.socialforce.scene.impl.SimpleParameterPool;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by Whatever on 2017/3/1.
 */
public class ApplicationForTurnDoorTest extends SimpleApplication implements Application {
    DistancePhysicalEntity template;
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            currentScene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(currentScene, template);
            GoalStrategy strategy = new NearestGoalStrategy(currentScene, pathFinder);
            strategy.pathDecision();
            this.initScene(currentScene);
            while (!toSkip()) {
                this.stepNext(currentScene);
            }
            if(onStop()) return;
        }
    }



    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        loader.setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(100,new Box2D(0,0,10,-10))
                .setValue(new BaseAgent(template, new Velocity2D(0,0)))
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(1,4,8,1)))
                .addValue(new Exit(new Box2D(4,-2,2,5)))
                //.addValue(new DoorTurn(new Box2D(new Point2D(4.5,0.25),new Point2D(4,0)))
                //.addValue(new DoorTurn(new Box2D(new Point2D(5.5,0.25),new Point2D(6,0)))
        );

        loader.readParameterSet(parameters);
        scenes = loader.readScene();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }

    private void registerOn(ApplicationLoader loader) {
        loader.add(this);
    }

}


