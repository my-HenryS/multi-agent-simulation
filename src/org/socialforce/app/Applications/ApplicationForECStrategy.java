package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.GoalDynamicColorMarkDrawer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.*;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.awt.*;
import java.io.InputStream;
import java.util.Iterator;


/**
 * Created by Whatever on 2016/12/15.
 */
public class ApplicationForECStrategy extends SimpleApplication implements Application {
    DistancePhysicalEntity template;

    public ApplicationForECStrategy(){
    }

    /**
     * start the application immediately.
     * TODO start和setUpstrategy重构，将strategy独立于scene
     */
    @Override
    public void start() {
        setUpScenes();
        System.out.println("Application starts!!");
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            currentScene = iterator.next();
            int iteration = 0;
            PathFinder pathFinder = new AStarPathFinder(currentScene, template);
            strategy = new ECStrategy(currentScene, pathFinder);
            //strategy = new DynamicLifeBeltStrategy(scene, pathFinder);
            //strategy = new LifeBeltStrategy(scene, pathFinder);
            //strategy = new NearestGoalStrategy(scene, pathFinder);
            strategy.pathDecision();
            while (!toSkip()) {
                this.StepNext(currentScene);
                iteration += 1;
                if(iteration % 500 ==0 && strategy instanceof DynamicStrategy){
                    ((DynamicStrategy) strategy).dynamicDecision();
                }
            }
            if(onStop()) return;
        }
    }

    @Override
    public boolean toSkip(){
        return Skip || currentScene.getAllAgents().size() < 5;
    }

    @Override
    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        loader.setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(405,new Box2D(4,4 ,27.5,15.5))
                .setValue(new BaseAgent(template, new Velocity2D(0,0)))
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new Exit(new Box2D(new Point2D(25,2), new Point2D(26.75,5))))
                .addValue(new Exit(new Box2D(new Point2D(31,13.5), new Point2D(34,14.5))))
                .addValue(new Exit(new Box2D(new Point2D(2,9.5), new Point2D(5,10.25))))
                .addValue(new Exit(new Box2D(new Point2D(13,19), new Point2D(14.5,22))))
                .addValue(new SafetyRegion(new Box2D(24,2,4,1)))
                .addValue(new SafetyRegion(new Box2D(33,12,1,4)))
                .addValue(new SafetyRegion(new Box2D(2,8,1,4)))
                .addValue(new SafetyRegion(new Box2D(12,21,4,1)))
        );

        loader.readParameterSet(parameters);
        scenes = loader.readScene();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }

    @Override
    public void manageDrawer(SceneDrawer drawer){
        drawer.setBackgroundColor(Color.BLACK);
        Drawer agentDrawer = drawer.getEntityDrawerInstaller().getSupport(Agent.class).getDrawer();
        if(agentDrawer instanceof GoalDynamicColorMarkDrawer) {
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(26, 2.5), Color.red);
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(33.5,14), Color.green);
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(14.0,21.5), Color.CYAN);
        }
        Drawer defaultDrawer = drawer.getEntityDrawerInstaller().getSupport(InteractiveEntity.class).getDrawer();
    }

}
