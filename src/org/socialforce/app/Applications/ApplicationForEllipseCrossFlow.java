package org.socialforce.app.Applications;

import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.GoalDynamicColorMarkDrawer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
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

/**窄（单人）
 * Created by Administrator on 2017/11/3 0003.
 */
public class ApplicationForEllipseCrossFlow extends SimpleApplication{
    DistancePhysicalEntity template;
    double CrossWidth;
    double TurnWidth;
    double TestWidth;
    public ApplicationForEllipseCrossFlow (){
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
                int timeStamp = 0;
                if(timeStamp % 33 == 0){
                    for(Iterator<InteractiveEntity> iter = currentScene.getStaticEntities().selectClass(Monitor.class).iterator(); iter.hasNext();){
                        Monitor monitor = (Monitor)iter.next();
                        double speed = monitor.sayVelocity();
                        double rho = monitor.sayRho();
                        System.out.println(speed+"\t"+rho);
                    }
                }
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
        CrossWidth = 0.8;   //FIXME 通道的宽度
        TurnWidth = 20.0;   //FIXME 主动侧身区域的长度(快速情况可延长)
        TestWidth = 1.0;    //FIXME moniter的监控范围

        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0,5.0+CrossWidth/2,40,0.4)),
                        new Wall(new Box2D(0,4.6-CrossWidth/2,40,0.4))
                }).setModel(new SimpleForceModel());

        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new CrossFlow(new Box2D(20-TurnWidth/2,5.0-CrossWidth/2,TurnWidth,CrossWidth)))
                .setPriority(5)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(15,new Box2D(1,5.0-CrossWidth/2,9,CrossWidth))
                .setValue(new BaseAgent(template, new Velocity2D(1.5,0)))
                .setGaussianParameter(1,0.015)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(15,new Box2D(30,5.0-CrossWidth/2,9,CrossWidth))
                .setValue(new BaseAgent(template, new Velocity2D(-1.5,0)))
                .setGaussianParameter(1,0.015)
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(46,4,1,1)))
                .addValue(new SafetyRegion(new Box2D(-6,5,1,1)))
                .addValue(new Monitor(new Box2D(20-TestWidth/2,5.0-CrossWidth/2,TestWidth,CrossWidth)))
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
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(-5.5,5.5), Color.green);  //SafetyRegion的中心点
        }

    }
}
