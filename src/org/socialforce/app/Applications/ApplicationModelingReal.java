package org.socialforce.app.Applications;

import org.socialforce.app.Applications.modeling.CSVReaderModelRev;
import org.socialforce.app.Applications.modeling.OverlappableCircle2D;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Model;
import org.socialforce.model.impl.*;
import org.socialforce.app.Applications.modeling.OverlappableAgent;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.*;
import org.socialforce.strategy.impl.StraightPath;

/**
 * Created by Ledenel on 2018/1/3.
 */
public class ApplicationModelingReal extends SimpleApplication{
    //纵向障碍物-宽门
    public Scene setMapA4(){
        CSVReaderModelRev model = new CSVReaderModelRev("/input/纵向障碍物-宽门-无奖励_2.csv", 1.0/30);
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(0, 0, 19.21, 10.77)),
                new Wall[]{
                        new Wall(new Box2D(3.41,9.75,9.05,0.53+5)), //上
                        new Wall(new Box2D(3.41,3.21-5,9.05,0.53+5)), //下
                        new Wall(new Box2D(3.41,3.74,0.53,6.01)), //左
                        //以上固定不动
                        new Wall(new Box2D(11.93,7.39,0.53,2.36)),//右上
                        new Wall(new Box2D(11.93,3.74,0.53,2.65)),//右下
                        //上边两个是门
                        new Wall(new Box2D(9.75,6.67,1.18,0.58)),//纵向障碍物


                }).setModel(new SimpleForceModel(1.0/30));
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new SafetyRegion(new Box2D(14.05,6.81-5,0.1+5,0.1+10+1)))  //不动
        );
        loader.readParameterSet(parameters);
        Scene first = loader.readScene().getFirst();

        first = loadPosition(model, first);

        for(Agent ag : first.getAllAgents()) {
            ag.setPath(new StraightPath(new Point2D(14.05+0.05, 6.81+0.05),new Point2D(14.05+0.05+1, 6.81+0.05)));
        }

        return first;
    }

    private Scene loadPosition(CSVReaderModelRev model, Scene first) {
        Scene sec;
        sec = first;
        Model old = first.getModel();
        first.setModel(model);
        int agent_num = model.agentCounts();
        for(int i=0;i<agent_num;i++) {
            sec.addEntity(new OverlappableAgent(new OverlappableCircle2D(new Point2D(0,0),0.486/2), new Velocity2D(0,0)));
        }
        first.stepNext();
        first.setModel(old);
        return sec;
    }


    public void setCurrentScene(Scene scene) {
        scene.setApplication(this);
        this.currentScene = scene;
    }

    @Override
    public void start() {
        setCurrentScene(setMapA4());
        //setCurrentScene(setMaptest());
        while(!this.toSkip()) {
            this.stepCurrent();
        }
    }
}
