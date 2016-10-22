package org.socialforce.app;

import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;
import org.socialforce.model.PathFinder;
import org.socialforce.model.SocialForceModel;
import org.socialforce.model.impl.StraightPath;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Whatever on 2016/10/22.
 */
public class SimpleApplication implements SocialForceApplication {
    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        generator = new SimpleSceneGenerator();
        singleScene = (Scene) generator.generate(loader);
        singleScene = (Scene) generator.generate(singleScene,new SimpleParameterSet());
        Agent agent;
        Point2D goal,temp;
        for (Iterator iter = singleScene.getAllAgents().iterator(); iter.hasNext();){
            //给所有agent设置path
            agent = (Agent)iter.next();
            goal = new Point2D(-1,5);
            temp = new Point2D(10,-1);
            if (temp.distanceTo(agent.getShape().getReferencePoint())<goal.distanceTo(agent.getShape().getReferencePoint())){
                goal = temp;
            }
            temp = new Point2D(10,14);
            if (temp.distanceTo(agent.getShape().getReferencePoint())<goal.distanceTo(agent.getShape().getReferencePoint())){
                goal = temp;
            }
            temp = new Point2D(24,10);
            if (temp.distanceTo(agent.getShape().getReferencePoint())<goal.distanceTo(agent.getShape().getReferencePoint())){
                goal = temp;
            }
            agent.setPath(new StraightPath(agent.getShape().getReferencePoint(),goal));
        }
    }
    private SimpleSceneGenerator generator;
    private SceneLoader loader = new SquareRoomLoader();

    protected SocialForceModel model;
    /**
     * get the social force model the application is using.
     *
     * @return the model.
     */
    @Override
    public SocialForceModel getModel() {
        return model;
    }

    /**
     * set the social force model for the application.
     *
     * @param model the model to be set.
     */
    @Override
    public void setModel(SocialForceModel model) {
        this.model = model;
    }


    protected Scene singleScene;
    /**
     * get all the scenes the applicaion is simulating.
     *
     * @return all scenes to simulate.
     */
    @Override
    public Iterable<Scene> getAllScenes() {
        return (Iterable<Scene>) singleScene;//TODO 加相关方法，不然不对
    }


    protected AgentEscapeListener listener;
    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public AgentEscapeListener getApplicationListener() {
        return listener;
    }

    /**
     * set a listener for application events.
     *
     * @param listener the listener to be set.
     */
    @Override
    public void setApplicationListener(AgentEscapeListener listener) {
        this.listener = listener;
    }

    @Override
    public Scene findScene(ValueSet set) {
        return singleScene;
    }

    protected  List<PathFinder> pathFinder;
    @Override
    public List<PathFinder> getAllPathFinders() {
        return pathFinder;
    }
}
