package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.gui.SceneShower;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.ETC_Tollbooth;
import org.socialforce.model.impl.Scenery;
import org.socialforce.model.impl.SimpleTollbooth;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.*;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.DynamicStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.*;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/1/20.
 */
public class ApplicationForMCM extends ApplicationForECTest implements SocialForceApplication {
    SceneShower shower;
    public void setShower(SceneShower shower){
        this.shower = shower;
    }
    public ApplicationForMCM(){

    }

    /**
     * start the application immediately.
     * TODO start和setUpstrategy重构，将strategy独立于scene
     */
    @Override
    public void start() {
        System.out.println("Application starts!!");
        int flag = 0;
        int []alpha = {100};
        int []beta =  {70};
        for(int i = 0; i < 1;i++){
            for(int a = 5; a<=12; a+= 1){
                setUpScenes(alpha[i],beta[i]);
                System.out.println("Scene is "+alpha[i]+","+beta[i]);
                for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
                    Scene scene = iterator.next();
                    int iteration = 0;
                    double volume = 0, scenery_num = 0;
                    double speed = 0;
                    int size = 0;
                    SVSR_RandomAgentGenerator agentGenerator = new SVSR_RandomTimeAgentGenerator(a,new Box2D(75, 4.5  ,46.5,10), new Velocity2D(0,13));
                    agentGenerator.apply(scene);
                    if(shower!=null)shower.getDrawerInstaller().addDrawerSupport(scene);
                    PathFinder pathFinder = new StraightPathFinder(scene);
                    strategy = new DynamicNearestGoalStrategy(scene, pathFinder);
                    strategy.pathDecision();
                    System.out.println("flow now "+a);
                    while (iteration <= 50000) {
                        scene.stepNext();
                        iteration += 1;
                        if(iteration % 60 ==0 && strategy instanceof DynamicStrategy){
                            ((DynamicStrategy) strategy).dynamicDecision();
                        }
                        if(iteration % 250 == 0){
                            agentGenerator.apply(scene);
                            if(shower!=null)shower.getDrawerInstaller().addDrawerSupport(scene);
                            strategy.pathDecision();
                        }
                        if(iteration % 1000 == 0){
                            for(Iterator<SceneValue> iter = scene.getValueSet().iterator(); iter.hasNext();){
                                SceneValue value = iter.next();
                                if(value instanceof SVSR_Scenery){
                                    double [] speeds = ((SVSR_Scenery)value).getValue().say();
                                    if(speeds!=null){
                                        speed += speeds[0];
                                        size += speeds[1];
                                    }
                                }
                            }
                        }
                    }
                    System.out.println("flow is "+speed/size);

                }
                /*
                if(flag == 1){
                    flag = 0;
                    System.out.println("-----------------------------------");
                    break;
                }*/

            }
        }

    }


    public void setUpScenes(double alpha, double beta){
        double width = 2.2, btm_length = 48, bottom = 5, top_length = 30;
        double tanA = Math.tan(alpha*(Math.PI/180)), tanB = Math.tan(beta*Math.PI/180);
        //梯形数值定义
        double xA = 76.5 + 14.25 * tanB / (tanA + tanB);
        double yA = bottom + btm_length + (14.25 * tanA * tanB) / (tanA + tanB);
        double lA = (28.5 * tanB / (tanA + tanB)) / Math.cos(alpha * (Math.PI/180));
        double xB = 123 - 14.25 * tanA / (tanA + tanB);
        double yB = yA;
        double lB = (28.5 * tanA / (tanA + tanB)) / Math.cos(beta * (Math.PI/180));
        double xC = 76.5 + 28.5 * tanB / (tanA + tanB)    +      1.6;
        double yC = bottom + btm_length + (28.5 * tanA * tanB) / (tanA + tanB);
        double top = yC + top_length;
        //静态部分
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(0, 0, 200,200)),
                new Wall[]{
                        //斜线 可调部分
                        new Wall(new Rectangle2D(new Point2D(xA,yA), lA, 3, alpha *(Math.PI/180))),
                        new Wall(new Rectangle2D(new Point2D(xB,yB), lB, 3, Math.PI - beta *(Math.PI/180))),
                        //上方直行道
                        new Wall(new Box2D(xC-width,yC,width,top_length)), //左
                        new Wall(new Box2D(xC+3.5,yC,0.4,top_length)),
                        new Wall(new Box2D(xC+7.3,yC,0.4,top_length)),
                        new Wall(new Box2D(xC+11.1,yC,0.4,top_length)),
                        new Wall(new Box2D(xC+14.9,yC,width,top_length)), //右
                        //下方直行道
                        new Wall(new Box2D(75,bottom,3,btm_length)),     //左
                        new Wall(new Box2D(81.5,bottom,width,btm_length)),
                        new Wall(new Box2D(87.2,bottom,width,btm_length)),
                        new Wall(new Box2D(92.9,bottom,width,btm_length)),
                        new Wall(new Box2D(98.6,bottom,width,btm_length)),
                        new Wall(new Box2D(104.3,bottom,width,btm_length)),
                        new Wall(new Box2D(110,bottom,width,btm_length)),
                        new Wall(new Box2D(115.7,bottom,width,btm_length)),
                        new Wall(new Box2D(121.5,bottom,3,btm_length)),  //右
                });
        ParameterPool parameters = new SimpleParameterPool();
        parameters.addLast(genParameter(new SVSR_SafetyRegion(new Box2D(xC,top - 1,17,1))));
        parameters.addLast(genParameter(new SVSR_Tollbooth( new ETC_Tollbooth(new Box2D(75,bottom+btm_length/2,29,1), 0.5) )));
        parameters.addLast(genParameter(new SVSR_Tollbooth( new SimpleTollbooth(new Box2D(104,bottom+btm_length/2,12,1), 4) )));
        parameters.addLast(genParameter(new SVSR_Tollbooth( new SimpleTollbooth(new Box2D(115.7,bottom+btm_length/2,6,1), 10) )));
        parameters.addLast(genParameter(new SVSR_Scenery(new Box2D(xC+1.75,top-top_length,0.4,0.4))));
        parameters.addLast(genParameter(new SVSR_Scenery(new Box2D(xC+5.55,top-top_length,0.4,0.4))));
        parameters.addLast(genParameter(new SVSR_Scenery(new Box2D(xC+9.3,top-top_length,0.4,0.4))));
        parameters.addLast(genParameter(new SVSR_Scenery(new Box2D(xC+13.2,top-top_length,0.4,0.4))));
        loader.readParameterSet(parameters);
        scenes = loader.readScene(this);
    }

    public SceneParameter genParameter(SceneValue... sceneValue){
        SceneParameter parameter;
        LinkedList<SceneValue> values = new LinkedList<>();
        for(SceneValue value : sceneValue){
            values.addLast(value);
        }
        parameter = new SimpleSceneParameter(values);
        return parameter;
    }

}

