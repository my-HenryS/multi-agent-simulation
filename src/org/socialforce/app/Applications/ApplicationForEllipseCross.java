package org.socialforce.app.Applications;

import org.socialforce.drawer.Drawer;
import org.socialforce.drawer.impl.GoalDynamicColorMarkDrawer;
import org.socialforce.drawer.impl.SceneDrawer;
import org.socialforce.geom.DistancePhysicalEntity;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Ellipse2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.FurthestGoalStrategy;

import java.awt.*;
import java.util.Iterator;

/**宽（多人）
 * Created by Administrator on 2017/12/4 0004.
 */
public class ApplicationForEllipseCross extends SimpleApplication {
    DistancePhysicalEntity template;
    public ApplicationForEllipseCross (){
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

        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(-50, -50, 100, 100)),
                new Wall[]{
                        new Wall(new Box2D(0,6,40,1)),
                        new Wall(new Box2D(0,3,40,1))  //两边的墙（在这里加墙）
                }).setModel(new SimpleForceModel());

        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(15,new Box2D(9,3,8,3))
                .setValue(new BaseAgent(template, new Velocity2D(3,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new RandomEntityGenerator2D(15,new Box2D(31,3,8,3))
                .setValue(new BaseAgent(template, new Velocity2D(-3,0)))
                .setGaussianParameter(1,0.025)
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(46,1,1,8)))
                .addValue(new SafetyRegion(new Box2D(-6,1,1,8)))
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
            ((GoalDynamicColorMarkDrawer) agentDrawer).addSupport(new Point2D(-5.5,5), Color.green);
        }

    }
}
