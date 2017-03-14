package org.socialforce.app.Applications;

import org.socialforce.app.Interpreter;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.app.impl.SceneStepDataProvider;
import org.socialforce.app.impl.SceneStepDumper;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.app.impl.SingleFileOutputer;
import org.socialforce.geom.DistanceShape;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.Monitor;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import static org.socialforce.scene.SceneLoader.genParameter;

/**
 * Created by sunjh1999 on 2017/1/13.
 */
public class ApplicationForCanteen extends SimpleApplication implements SocialForceApplication {
    DistanceShape template;

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
                Scene scene = iterator.next();
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
                for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(Agent.class).iterator(); iter.hasNext();){
                    Agent agent = (Agent) iter.next();
                    total_num ++;
                }
                System.out.print("Population of "+total_num);
                int iteration = 0;
                PathFinder pathFinder = new AStarPathFinder(scene, template);
                strategy = new ECStrategy(scene, pathFinder);
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
                while (scene.getAllAgents().size() > 10) {
                    long start = System.currentTimeMillis(), span, fps = 0;
                    scene.stepNext();
                    iteration += 1;
                    if(iteration % 500 ==0 && strategy instanceof DynamicStrategy){
                        ((DynamicStrategy) strategy).dynamicDecision();
                    }
                    long l = System.currentTimeMillis() - start;
                    span = l > fps? 0: fps - l;
                    try {
                        Thread.sleep(span); //锁帧大法
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*
                double samplewidth = 1;
                double [][] matrixV = new double[(int)(21/samplewidth)][(int)(30/samplewidth)];
                for(Iterator<InteractiveEntity> iter = scene.getStaticEntities().selectClass(Monitor.class).iterator(); iter.hasNext();){
                    Monitor m = (Monitor)iter.next();
                    Point p = m.getShape().getReferencePoint();
                    matrixV[(int)Math.rint((p.getY())/samplewidth)][(int)Math.rint(p.getX()/samplewidth)] = m.sayVolume();
                }
                for(double [] m1:matrixV){
                    for(double m:m1){
                        System.out.print(String.format("%.1f", m)+"\t");
                    }
                    System.out.println();
                }
                */
                System.out.println(iteration * 0.002);
            }
        }
    }


    public void setUpScenes(){
        template = new Circle2D(new Point2D(0,0),0.486/2);
        //File file = new File("/Users/sunjh1999/IdeaProjects/SocialForceSimulation/test/org/socialforce/app/impl/canteen2.s");
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("canteen2.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader();
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SV_RandomAgentGenerator(355,new Box2D(0,0,25,18),template)));
        parameters.addLast(genParameter(new SV_RandomAgentGenerator(155,new Box2D(0,18,25,3),template)));
        parameters.addLast(genParameter((new SV_SafetyRegion(new Box2D(-3,-0.5,1,4)))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(18.5,-3,4,1))));
        parameters.addLast(genParameter(new SV_SafetyRegion(new Box2D(30,17.5,1,4))));
        parameters.addLast(genParameter(new SV_Exit(new Box2D[]{new Box2D(new Point2D(-2,0.75), new Point2D(1.8,2.18)),
                new Box2D(new Point2D(3,0.82), new Point2D(4,2.18)),
                new Box2D(new Point2D(19.82,2), new Point2D(21.18,3)),
                new Box2D(new Point2D(19.82,-2), new Point2D(21.18,1.8)),
                new Box2D(new Point2D(24,18.82), new Point2D(26.2,20.18)),
                new Box2D(new Point2D(27.5,18.82), new Point2D(29.5,20.18)),
                })));

        for (int i = 5;i<=15;i=i+2){
            for (int j =4;j<=17;j++)
            parameters.addLast(genParameter(new SV_Exit(new Box2D[]{
                    new Box2D(new Point2D(i-0.1,j-0.1), new Point2D(i+1.1,j+0.1)),
            })));
        }

        for (int i = 17;i<=19;i=i+2){
            for (int j =5;j<=17;j++)
                parameters.addLast(genParameter(new SV_Exit(new Box2D[]{
                        new Box2D(new Point2D(i-0.1,j-0.1), new Point2D(i+1.1,j+0.1)),
                })));
        }

        for (int i = 21;i<=21;i=i+2){
            for (int j =9;j<=17;j++)
                parameters.addLast(genParameter(new SV_Exit(new Box2D[]{
                        new Box2D(new Point2D(i-0.1,j-0.1), new Point2D(i+1.1,j+0.1)),
                })));
        }


        for (int i = 0;i<=1;i++){
            for (int j =9;j<=17;j++)
                parameters.addLast(genParameter(new SV_Exit(new Box2D[]{
                        new Box2D(new Point2D(i*2.5-0.1,j-0.1), new Point2D(i*2.5+1.1,j+0.1)),
                })));
        }

        /*
        double samplewidth = 1;
        for (int i = 0; i< 30/samplewidth;i++){
            for (int j = 0; j < 21/samplewidth; j++){
               parameters.addLast(genParameter(new SV_Monitor(new Circle2D(new Point2D(i*samplewidth,j*samplewidth),samplewidth/2))));
            }
        }
        */
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }



}
