package org.socialforce.app.Applications;

import com.opencsv.CSVWriter;
import org.socialforce.app.Applications.modeling.*;
import org.socialforce.geom.impl.Box2D;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.geom.impl.Velocity2D;
import org.socialforce.model.Agent;
import org.socialforce.model.Model;
import org.socialforce.model.impl.SafetyRegion;
import org.socialforce.model.impl.Wall;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.SimpleEntityGenerator;
import org.socialforce.scene.impl.SimpleParameterPool;
import org.socialforce.scene.impl.SimpleScene;
import org.socialforce.scene.impl.StandardSceneLoader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by Ledenel on 2018/1/3.
 */
public class ApplicationModelingReal extends SimpleApplication {

    private final SocialForce socialForce = new SocialForce(1.0 / 30);
    private final String filename = "/input/纵向障碍物-宽门-无奖励_2.csv";
    private CSVReaderModelRev realModel = new CSVReaderModelRev(filename, 1.0 / 30);

    //纵向障碍物-宽门
    public Scene setMapA4() {
        realModel = new CSVReaderModelRev(filename, 1.0 / 30);
        SceneLoader loader = new StandardSceneLoader(new SimpleScene(new Box2D(0, 0, 19.21, 10.77)),
                new Wall[]{
                        new Wall(new Box2D(3.41, 9.75, 9.05, 0.53 + 5)), //上
                        new Wall(new Box2D(3.41, 3.21 - 5, 9.05, 0.53 + 5)), //下
                        new Wall(new Box2D(3.41, 3.74, 0.53, 6.01)), //左
                        //以上固定不动
                        new Wall(new Box2D(11.93, 7.39, 0.53, 2.36)),//右上
                        new Wall(new Box2D(11.93, 3.74, 0.53, 2.65)),//右下
                        //上边两个是门
                        new Wall(new Box2D(9.75, 6.67, 1.18, 0.58)),//纵向障碍物


                })
                .setModel(realModel)
                .setModel(socialForce);
        SimpleParameterPool parameters = new SimpleParameterPool();
        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new SafetyRegion(new Box2D(14.05 - 0.95, 6.81 - 5, 0.1 + 5, 0.1 + 10 + 1)))  //不动
        );
        loader.readParameterSet(parameters);
        Scene first = loader.readScene().getFirst();

        first = loadPosition(realModel, first);

        for (Agent ag : first.getAllAgents()) {
            ag.setPath(new CheckpointPath(
                    new Box2D(
                            new Point2D(11.375, 7.8),
                            new Point2D(12.375, 5.1)
                    ),
                    new Circle2D(
                            new Point2D(11.85, 6.978), 0.7
                    ),
                    new Circle2D(
                            new Point2D(14.119, 6.978), 0.7
                    )
            ));
        }

        return first;
    }

    private Scene loadPosition(CSVReaderModelRev model, Scene first) {
        Scene sec;
        sec = first;
        Model old = first.getModel();
        first.setModel(model);
        int agent_num = model.agentCounts();
        for (int i = 0; i < agent_num; i++) {
            sec.addEntity(new OverlappableAgent(new OverlappableCircle2D(new Point2D(0, 0), 0.486 / 2), new Velocity2D(0, 0)));
        }
        first.stepNext();
        first.setModel(old);
        return sec;
    }


    public void setCurrentScene(Scene scene) {
        scene.setApplication(this);
        this.currentScene = scene;
    }

    Diff sceneDiff(Scene a, Scene b) {
        double dis = 0;
        SafetyRegion sf = (SafetyRegion) b.getStaticEntities().stream()
                .filter(x -> x instanceof SafetyRegion).findFirst().orElse(null);
        dis = a.getAllAgents().stream()
                .mapToDouble(ag ->
                        b.getAllAgents().stream()
                                .mapToDouble(x -> ag.getPhysicalEntity().getReferencePoint().distanceTo(x.getPhysicalEntity().getReferencePoint()))
                                .min()
                                .orElseGet(() -> {
                                    double vc = sf.getPhysicalEntity().getDistance(ag.getPhysicalEntity().getReferencePoint());
                                    return vc < 0 ? 0 : vc;
                                }))
                .sum();
        return new Diff(a.getAllAgents().size(), dis);
    }

    @Override
    public void start() {
        setCurrentScene(setMapA4());
        Scene real = setMapA4();
        real.setModel(realModel);
        //setCurrentScene(setMaptest());
        CSVWriter wr = null;
        try {
            wr = new CSVWriter(new FileWriter("out_step_seq.csv"));
            for (int tick = 1; currentScene.getAllAgents().size() > 0 || real.getAllAgents().size() > 0; tick++) {
                this.stepCurrent();
                real.stepNext();

                double realtime = tick / 30.0;

                Diff x = sceneDiff(currentScene, real);
                Diff y = sceneDiff(real, currentScene);

                String[] sts = Stream.of(realtime, x.num, x.dis, y.num, y.dis)
                        .map(String::valueOf)
                        .toArray(String[]::new);

                wr.writeNext(sts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //this.Skip = false;
    }

    class Diff {
        public int num;
        public double dis;

        public Diff(int num, double dis) {
            this.num = num;
            this.dis = dis;
        }
    }
}
