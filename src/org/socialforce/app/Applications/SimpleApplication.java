package org.socialforce.app.Applications;

import org.socialforce.app.*;
import org.socialforce.app.impl.preset.SVSR_AgentGenerator;
import org.socialforce.app.impl.preset.SVSR_Exit;
import org.socialforce.app.impl.preset.SVSR_SafetyRegion;
import org.socialforce.app.impl.preset.SquareRoomLoader;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.*;
import org.socialforce.model.impl.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Whatever on 2016/10/22.
 */
public class SimpleApplication implements SocialForceApplication {
    public SimpleApplication() {
        //singleScene = loader.readScene();
        singleScene.setApplication(this);
        SetUp();
    }

    public void SetUp() {
        SVSR_Exit exit = new SVSR_Exit();
        SVSR_AgentGenerator agentGenerator = new SVSR_AgentGenerator(2, 2, 1, new Box2D(3, 3, 22, 12));
        SVSR_SafetyRegion safetyRegion = new SVSR_SafetyRegion();
        exit.setValue((new Box2D[]{new Box2D(-1, 5, 4, 6), new Box2D(10, -1, 6, 4), new Box2D(10, 14, 6, 4), new Box2D(24, 6, 4, 6)}));
        exit.apply(singleScene);
        agentGenerator.apply(singleScene);
        safetyRegion.setValue(new SafetyRegion(new Box2D(-2, 5, -4, 6)));
        safetyRegion.apply(singleScene);
        safetyRegion.setValue(new SafetyRegion(new Box2D(10, -1, 6, -4)));
        safetyRegion.apply(singleScene);
        safetyRegion.setValue(new SafetyRegion(new Box2D(10, 19, 6, 4)));
        safetyRegion.apply(singleScene);
        safetyRegion.setValue(new SafetyRegion(new Box2D(28, 6, 4, 6)));
        safetyRegion.apply(singleScene);
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(0,0,1,7), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(0,11,1,5), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(1,0,11,1), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(15,0,11,1), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(1,15,10,1), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(15,15,10,1), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(25,1,1,7), SimpleSocialForceModel.STATIC_TYPE_WALL));
        singleScene.getStaticEntities().add(model.createStatic(new Box2D(25,12,1,4), SimpleSocialForceModel.STATIC_TYPE_WALL));
        //为解决穿墙的问题把墙每隔一米切分成多个墙
        /*
        for (InteractiveEntity entity : singleScene.getStaticEntities()){
            if (entity instanceof ComplexBox2D){
                Box2D[] temp = ((ComplexBox2D) entity).BreakDown();
                Box2D[] temp1;
                List<Box2D> list = new ArrayList<Box2D>();
                double Xmin = ((ComplexBox2D) entity).getBounds().getStartPoint().getX();
                double Ymin = ((ComplexBox2D) entity).getBounds().getStartPoint().getY();
                double Xmax = ((ComplexBox2D) entity).getBounds().getEndPoint().getX();
                double Ymax = ((ComplexBox2D) entity).getBounds().getEndPoint().getY();
                double min = Xmin,max = Xmax;
                int flag =0;
                if (Ymax - Ymin > Xmax - Xmin){min = Ymin;max = Ymax;flag = 1;}
                for (double a = min;a<max;a++){
                    for (int b = 0;b < temp.length;b++) {
                        if (flag == 0) {
                            if (temp[b].contains(new Point2D(a, Ymax)) && temp[b].contains(new Point2D(a + 1, Ymax))) {
                                list.add(new Box2D(new Point2D(a, Ymin), new Point2D(a + 1, Ymax)));
                            }
                        }
                        if (flag == 1) {
                            if (temp[b].contains(new Point2D(Xmax, a)) && temp[b].contains(new Point2D(Xmax, a+1))) {
                                list.add(new Box2D(new Point2D(Xmin,a ), new Point2D(Xmax, a+1)));
                            }
                        }
                    }
                }
                temp1 = list.toArray(new Box2D[1]);
                for (int i = 0; i < temp1.length;i++){
                    singleScene.getStaticEntities().add(new Wall(temp1[i]));
                }
                singleScene.getStaticEntities().remove(entity);
            }
        }
        */
    }

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        Agent agent;
        Point2D goal, temp;
        for (Iterator iter = singleScene.getAllAgents().iterator(); iter.hasNext(); ) {
            //给所有agent设置path
            agent = (Agent) iter.next();
            goal = new Point2D(-3, 8);
            temp = new Point2D(13, -3);
            if (temp.distanceTo(agent.getShape().getReferencePoint()).length() < goal.distanceTo(agent.getShape().getReferencePoint()).length()) {
                goal = temp;
            }
            temp = new Point2D(13, 20);
            if (temp.distanceTo(agent.getShape().getReferencePoint()).length() < goal.distanceTo(agent.getShape().getReferencePoint()).length()) {
                goal = temp;
            }
            temp = new Point2D(30, 9);
            if (temp.distanceTo(agent.getShape().getReferencePoint()).length() < goal.distanceTo(agent.getShape().getReferencePoint()).length()) {
                goal = temp;
            }
            switch ((int) goal.getY()) {
                case -3:
                    temp = new Point2D(13, 1);
                    break;
                case 8:
                    temp = new Point2D(1, 8);
                    break;
                case 20:
                    temp = new Point2D(13, 13);
                    break;
                case 9:
                    temp = new Point2D(23, 9);
                    break;
            }
            //agent.setPath(new StraightPath(agent.getShape().getReferencePoint(), goal));
            //System.out.println(agent.getPath().toString());
            agent.setPath(new AStarPathFinder(singleScene, agent, goal).plan_for());
        }

        while (!singleScene.getAllAgents().isEmpty()) {
            singleScene.stepNext();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    protected SceneLoader loader = new SquareRoomLoader();

    protected SocialForceModel model = new SimpleSocialForceModel();

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


    protected Scene singleScene = new SimpleScene(new Box2D(-50, -50, 100, 100));

    /**
     * get all the scenes the applicaion is simulating.
     *
     * @return all scenes to simulate.
     */
    @Override
    public Iterable<Scene> getAllScenes() {
        return Stream.of(singleScene)::iterator;
    }


    protected ApplicationListener listener;

    /**
     * get the application listener for the application.
     *
     * @return the application listener.
     */
    @Override
    public ApplicationListener getApplicationListener() {
        return listener;
    }

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
        return singleScene;
    }

    protected List<PathFinder> pathFinder;

    @Override
    public List<PathFinder> getAllPathFinders() {
        return pathFinder;
    }
}
