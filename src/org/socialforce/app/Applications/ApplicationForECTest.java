package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.SP_SingleExitWidth;
import org.socialforce.app.impl.preset.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.AStarPathFinder;
import org.socialforce.model.impl.SimpleSocialForceModel;
import org.socialforce.model.impl.StraightPath;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/2.
 */
public class ApplicationForECTest implements SocialForceApplication {

    public ApplicationForECTest(){
        SetUpParameter();
        SetUpValues();
        setUpScenes();
        setUpStrategy();
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        while (true) {
            int i = 0;
            for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
                Scene scene = iterator.next();
                if (!scene.isFreeze()) {
                    scene.stepNext();
                }
                else i++;
            }
            if (i == scenes.size()){
                break;
            }
        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     * 目前就先随便生成一堆算了……
     * 手动map
     */
    public void setUpScenes(){
        for (Iterator<SceneValue> iterator = SV_exit1.iterator(); iterator.hasNext();)
        {
            SceneValue exit = iterator.next();
            SceneLoader loader = new ECTestLoader();
            Scene scene = loader.readScene();
            for (Iterator<SceneValue> iterator1 = statics.iterator();iterator1.hasNext();){
                SceneValue sceneValue = iterator1.next();
                sceneValue.apply(scene);
            }
            exit.apply(scene);
            scene.setApplication(this);
            scenes.addLast(scene);
        }
    }

    protected ParameterSet parameterSet;//目前先不用这个，之后肯定要用
    protected Iterable<SceneValue> SV_exit1;
    LinkedList<SceneValue> statics;

    public void SetUpParameter(){/*
        SimpleSceneParameter parameter = new SimpleSceneParameter();
        parameter.addValue(new SVSR_Exit(new Box2D[]{new Box2D(9,-2,2,5)}));
        //parameter.addValue(new SVSR_AgentGenerator(0.5,0.5,1,new Box2D(5,-5,10,3)));
        parameter.addValue(new SVSR_RandomAgentGenerator(100,new Box2D(5,-5,10,3)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(6,1,8,1)));
        parameters.addLast(parameter);*/


        SP_SingleExitWidth exit1 = new SP_SingleExitWidth();
        exit1.setPosition(new Point2D(10,0.5));
        exit1.setExitDirection(true);
        exit1.setWidths(0.5,2);
        SV_exit1 = exit1.sample(5);
    }

    /**
     * TODO 这部分是一个临时类，
     * 目前也可以认为是场景的“静态”的部分，不枚举他们。
     * 之后对应的parameter一个个实现后都会挪到SetUpParameter里
     * 最后把所有SetUpParameter整个挪到ParameterSet里
     * 注意上一句话中，前一个Set指设置，后一个Set指集合
     */
    public void SetUpValues(){
        SVSR_RandomAgentGenerator generator = new SVSR_RandomAgentGenerator(10,new Box2D(5,-5,10,3));
        SVSR_SafetyRegion safetyRegion = new SVSR_SafetyRegion(new Box2D(6,1,8,1));
        statics = new LinkedList<>();
        statics.addLast(generator);
        statics.addLast(safetyRegion);
    }

    public void setUpStrategy(){
        Agent agent;
        Scene scene;
        Point2D goal;
        for (Iterator<Scene> iterator = scenes.iterator();iterator.hasNext();){
            scene = iterator.next();
            for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
                 //给所有agent设置path
                 agent = (Agent) iter.next();
                 goal = new Point2D(10, 8);
                 //agent.setPath(new StraightPath(agent.getShape().getReferencePoint(), goal));
                 //System.out.println(agent.getPath().toString());
                 agent.setPath(new AStarPathFinder(scene, agent, goal).plan_for());
                 //agent.setPath(new StraightPath(new Point2D(9,8), goal));
             }
        }
    }

    /**
     * get the social force model the application is using.
     *
     * @return the model.
     */
    @Override
    public SocialForceModel getModel() {
        return model;
    }
    protected SocialForceModel model = new SimpleSocialForceModel();

    /**
     * set the social force model for the application.
     *
     * @param model the model to be set.
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }

    /**
     * get all the scenes the applicaion is simulating.
     *
     * @return all scenes to simulate.
     */
    @Override
    public Iterable<Scene> getAllScenes() {
        return scenes;
    }
    protected LinkedList<Scene> scenes = new LinkedList<>();

    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public ApplicationListener getApplicationListener() {
        return listener;
    }
    protected ApplicationListener listener;

    /**
     * set a listener for application events.
     *
     * @param listener the listener to be set.
     */
    @Override
    public void setApplicationListener(ApplicationListener listener) {
        this.listener = listener;
    }

    @Override
    public Scene findScene(ValueSet set) {
        return null;
    }

    @Override
    public List<PathFinder> getAllPathFinders() {
        return null;
    }
}
