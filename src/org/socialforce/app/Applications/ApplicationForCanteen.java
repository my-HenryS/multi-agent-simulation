package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.Application;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.BaseAgent;
import org.socialforce.model.impl.Exit;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.SimpleForceModel;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.io.InputStream;
import java.util.Iterator;


/**
 * Created by sunjh1999 on 2017/1/13.
 */
public class ApplicationForCanteen extends SimpleApplication implements Application {
    BaseAgent template;

    public ApplicationForCanteen(){

    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        System.out.println("Application starts!!");
        for(int i = 0; i < 40; i++){
            setUpScenes();
            for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
                currentScene = iterator.next();
                // TODO: 2017/1/29 Refactor dump setting code here.
                /*
                SceneStepDataProvider rootProvider = new SceneStepDataProvider();
                try {
                    SceneStepDumper dumper = new SceneStepDumper(new SingleFileOutputer("testDump" +
                            i +
                            ".txt"));
                    rootProvider.addListener(dumper);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                scene.addSceneListener(rootProvider);
                */
                // dump code end.
                int total_num = 0;
                for(Iterator<InteractiveEntity> iter = currentScene.getStaticEntities().selectClass(Agent.class).iterator(); iter.hasNext();){
                    total_num ++;
                }
                System.out.print("Population of "+total_num);
                int iteration = 0;
                PathFinder pathFinder = new AStarPathFinder(currentScene, template.getPhysicalEntity(), 0.2);
                strategy = new ECStrategy(currentScene, pathFinder);
                /*
                if(i<10){
                    System.out.print(" with ECStrategy, ");
                    strategy = new ECStrategy(scene, pathFinder);
                }
                else if(i<20){
                    System.out.print(" with DynamicLBStrategy, ");
                    strategy = new DynamicLifeBeltStrategy(scene, pathFinder);
                }
                else if(i<30){
                    System.out.print(" with LifeBeltStrategy, ");
                    strategy = new LifeBeltStrategy(scene, pathFinder);
                }
                else if(i<40){
                    System.out.print(" with NRStrategy, ");
                    strategy = new NearestGoalStrategy(scene, pathFinder);
                }
                */
                strategy.pathDecision();
                this.initScene(currentScene);
                while (!toSkip()) {
                    this.stepNext(currentScene);
                    iteration += 1;
                    if(iteration % 500 ==0 && strategy instanceof DynamicStrategy){
                        ((DynamicStrategy) strategy).dynamicDecision();
                    }
                }
                if(onStop()) return;

                System.out.println(iteration * 0.002);
            }
        }
    }

    @Override
    public boolean toSkip(){
        return Skip || currentScene.getAllAgents().size() < 10;
    }


    public void setUpScenes(){
        template = new BaseAgent(new Circle2D(new Point2D(0,0),0.486/2), new Velocity2D(0,0));
        //File file = new File("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/test/org/socialforce/app/impl/canteen2.s");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("canteen2.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        loader.setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();

        parameters.addValuesAsParameter(
                new RandomEntityGenerator2D(155,new Box2D(0,0,25,18)).setValue(template).setPriority(-2),
                new RandomEntityGenerator2D(155,new Box2D(0,0,25,18)).setValue(template).setPriority(-2)
        );

        parameters.addValuesAsParameter(
                new RandomEntityGenerator2D(75,new Box2D(0,18,25,3)).setValue(template).setPriority(-2)
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(-3,-0.5,1,4)))
                .addValue(new SafetyRegion(new Box2D(18.5,-3,4,1)))
                .addValue(new SafetyRegion(new Box2D(30,17.5,1,4)))
                .setCommonName("SafetyRegion")
        );

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new Exit(new Box2D(new Point2D(-2,0.75), new Point2D(1.8,2.18))))
                .addValue(new Exit(new Box2D(new Point2D(3,0.82), new Point2D(4,2.18))))
                .addValue(new Exit(new Box2D(new Point2D(19.82,2), new Point2D(21.18,3))))
                .addValue(new Exit(new Box2D(new Point2D(19.82,-2), new Point2D(21.18,1.8))))
                .addValue(new Exit(new Box2D(new Point2D(24,18.82), new Point2D(26.2,20.18))))
                .addValue(new Exit(new Box2D(new Point2D(27.5,18.82), new Point2D(29.5,20.18))))
                .setCommonName("Exit")
                .setPriority(2)
        );

        /*parameters.addValuesAsParameter(new EntityGenerator2D(new Point2D(5.5,4),2,1,6,14)
                .setValue(new Exit(new Box2D(0,0,1.2,0.2)))
        );

        parameters.addValuesAsParameter(new EntityGenerator2D(new Point2D(17.5,5),2,1,2,13)
                .setValue(new Exit(new Box2D(0,0,1.2,0.2)))
        );

        parameters.addValuesAsParameter(new EntityGenerator2D(new Point2D(21.5,9),2,1,1,9)
                .setValue(new Exit(new Box2D(0,0,1.2,0.2)))
        );

        parameters.addValuesAsParameter(new EntityGenerator2D(new Point2D(0.5,9),2.5,1,2,9)
                .setValue(new Exit(new Box2D(0,0,1.2,0.2)))
        );*/

        /*

        double samplewidth = 1;
        for (int i = 0; i< 30/samplewidth;i++){
            for (int j = 0; j < 21/samplewidth; j++){
               parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(i*samplewidth,j*samplewidth),samplewidth/2))));
            }
        }
        */


        loader.readParameterSet(parameters);
        scenes = loader.readScene();
        for(Scene scene:scenes){
            scene.setApplication(this);
        }
    }



}
