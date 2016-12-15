package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.SimpleSceneParameter;
import org.socialforce.app.impl.preset.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Semicircle2D;
import org.socialforce.model.Agent;
import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.AStarPathFinder;
import org.socialforce.model.impl.SimpleSocialForceModel;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Whatever on 2016/12/15.
 */
public class ApplicationForECStrategy implements SocialForceApplication{

    public ApplicationForECStrategy(){
        SetUpParameter();
        setUpScenes();
        setUpStrategy();
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext();){
            Scene scene = iterator.next();
            while (!scene.getAllAgents().isEmpty()) {
                scene.stepNext();
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     * 目前就先随便生成一堆算了……
     * 手动map
     */
    public void setUpScenes(){
        for (Iterator<SceneParameter> iterator = parameters.iterator(); iterator.hasNext();)
        {
            SceneParameter parameter = iterator.next();
            SceneLoader loader = new ECTestLoader2();
            Scene scene = loader.readScene();
            if (parameter instanceof SimpleSceneParameter){
                while (true){
                    SceneValue sceneValue =((SimpleSceneParameter) parameter).removeValue();
                    if (sceneValue == null){break;}
                    else sceneValue.apply(scene);
                }
            }
            scene.setApplication(this);
            scenes.addLast(scene);
        }
    }

    protected ParameterSet parameterSet;//目前先不用这个，之后肯定要用
    protected LinkedList<SceneParameter> parameters = new LinkedList<>();

    public void SetUpParameter(){
        SimpleSceneParameter parameter = new SimpleSceneParameter();
        parameter.addValue(new SVSR_RandomAgentGenerator(100,new Box2D(4.5,4.5,15.5,27.5)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(24,2,4,1)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(33,12,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(2,8,1,4)));
        parameter.addValue(new SVSR_SafetyRegion(new Box2D(12,21,4,1)));
        parameters.addLast(parameter);
    }

    public void setUpStrategy(){
        Agent agent;
        Scene scene;
        Point2D goal,temp,present;
        for (Iterator<Scene> iterator = scenes.iterator();iterator.hasNext();){
            scene = iterator.next();
            for (Iterator iter = scene.getAllAgents().iterator(); iter.hasNext(); ) {
                //给所有agent设置path
                agent = (Agent) iter.next();
                present = (Point2D) agent.getShape().getReferencePoint();
                goal = new Point2D((25+26.75)/2,2);
                temp = new Point2D(34,14);
                if (present.distanceTo(temp)<=present.distanceTo(goal)){
                    goal = temp;
                }
                temp = new Point2D((13+14.5)/2,22);
                if (present.distanceTo(temp)<=present.distanceTo(goal)){
                    goal = temp;
                }
                temp = new Point2D(2,(9.5+10.25)/2);
                if (present.distanceTo(temp)<=present.distanceTo(goal)){
                    goal = temp;
                }
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
