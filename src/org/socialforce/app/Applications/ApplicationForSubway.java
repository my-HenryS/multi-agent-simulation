package org.socialforce.app.Applications;

import org.socialforce.app.Application;
import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.GoalDynamicColorMarkDrawer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.impl.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.FurthestGoalStrategy;

import java.awt.*;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/12/6 0006.
 */
public class ApplicationForSubway extends SimpleApplication implements Application {
    DistancePhysicalEntity template;
    double DoorWidth;
    double TurnWidth;
    public ApplicationForSubway (){
    }
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            currentScene = iterator.next();
            PathFinder pathFinder = new AStarPathFinder(currentScene, template);
            GoalStrategy strategy = new FurthestGoalStrategy(currentScene, pathFinder);
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
        //template = new Circle2D(new Point2D(0,0),0.45/2);
        template = new Ellipse2D(0.45/2,0.25/2,new Point2D(0,0),0);  //FIXME 行人的形状切换为椭圆
        DoorWidth = 1.3;   //FIXME 地铁门的宽度
        TurnWidth = 0.6;   //FIXME 主动侧身区域的长度

        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 50, 50)),
                new Wall[]{
                        new Wall(new Box2D(17.6,9.4,3.4,0.4)),
                        new Wall(new Box2D(17.6,2.0,3.4,0.4)),
                        new Wall(new Box2D(17.6,2.4,0.4,7.0))
                }).setModel(new SimpleForceModel());

        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new CrossFlow(new Box2D(17.8-TurnWidth/2,6.6,TurnWidth,DoorWidth)))
                .setPriority(5)
        );

        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new CrossFlow(new Box2D(17.8-TurnWidth/2,5.2-DoorWidth,TurnWidth,DoorWidth)))
                .setPriority(5)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(4,new Box2D(14.0,3.3,1.6,0.6))
                .setValue(new BaseAgent(template, new Velocity2D(1.5,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(4,new Box2D(14.0,5.5,1.6,0.6))
                .setValue(new BaseAgent(template, new Velocity2D(1.5,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(3,new Box2D(14.0,6.0,1.6,0.6))
                .setValue(new BaseAgent(template, new Velocity2D(1.5,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(4,new Box2D(14.0,7.9,1.6,0.6))
                .setValue(new BaseAgent(template, new Velocity2D(1.5,0)))
                .setGaussianParameter(1,0.025)
        );


        parameters.addValuesAsParameter(new RandomEntityGenerator2D(7,new Box2D(18.0,3.5,2.0,2.0))
                .setValue(new BaseAgent(template, new Velocity2D(-1.5,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(8,new Box2D(18.0,6.2,2.0,2.0))
                .setValue(new BaseAgent(template, new Velocity2D(-1.5,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(20.6,2.4,0.4,7)))
                .addValue(new SafetyRegion(new Box2D(10,2.4,0.4,7)))
        );

        loader.readParameterSet(parameters);
        scenes = loader.readScene();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }

    @Override
    public void manageDrawer(SceneDrawer drawer){
        Drawer agentDrawer = drawer.getEntityDrawerInstaller().getSupport(Agent.class).getDrawer();
        if(agentDrawer instanceof GoalDynamicColorMarkDrawer) {
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(8.2,5.9), Color.green);  //SafetyRegion的中心点
        }
    }

}
