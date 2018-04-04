package org.socialforce.app.Applications;

import org.socialforce.app.Application;
import org.socialforce.app.Interpreter;
import org.socialforce.app.impl.AgentStepCSVWriter;
import org.socialforce.app.impl.SimpleInterpreter;
import org.socialforce.geom.Velocity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.InteractiveEntity;
import org.socialforce.model.impl.*;
import org.socialforce.scene.Scene;
import org.socialforce.scene.SceneLoader;
import org.socialforce.scene.impl.MultipleEntitiesGenerator;
import org.socialforce.scene.impl.RandomEntityGenerator2D;
import org.socialforce.scene.impl.SimpleEntityGenerator;
import org.socialforce.scene.impl.SimpleParameterPool;
import org.socialforce.strategy.GoalStrategy;
import org.socialforce.strategy.PathFinder;
import org.socialforce.strategy.impl.AStarPathFinder;
import org.socialforce.strategy.impl.NearestGoalStrategy;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by sunjh1999 on 2017/7/4.
 */
public class ApplicationForEllipse extends SimpleApplication implements Application {
    BaseAgent template;
    double DoorWidth;
    int density;

    /**
     * start the application immediately.
     */
    @Override
    public void start() {
        setUpScenes();
        for (Iterator<Scene> iterator = scenes.iterator(); iterator.hasNext(); ) {
            AgentStepCSVWriter csvWriter = new AgentStepCSVWriter();
            currentScene = iterator.next();
            currentScene.addSceneListener(csvWriter);
            PathFinder pathFinder = new AStarPathFinder(currentScene, new Circle2D(new Point2D(0, 0), 0.25 / 2));
            GoalStrategy strategy = new NearestGoalStrategy(currentScene, pathFinder);
            strategy.pathDecision();
            this.initScene(currentScene);
            while (!toSkip()) {
                this.stepNext(currentScene);
                int timeStamp = 0;
                if(timeStamp%100==0){
                for (Iterator<InteractiveEntity> iter = currentScene.getStaticEntities().selectClass(Monitor.class).iterator();iter.hasNext();){
                    Monitor monitor = (Monitor)iter.next();
                    Double speed = monitor.sayVelocity();
                    double rho = monitor.sayRho();
                    System.out.println(speed+"\t"+rho);
                }
                }
            }
            csvWriter.writeCSV("output/Ellipse.csv");
//            Reader();
            if (onStop()) return;
        }
    }

    /**
     * 需要根据parameter的map来生成一系列scene
     */
    @Override
    public void setUpScenes() {
        //template = new BaseAgent(new Circle2D(new Point2D(0,0),0.486/2), new Velocity2D(0,0));   //FIXME 行人的形状切换为圆
        template = new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(0, 0), 0), new Velocity2D(0, 0));  //FIXME 行人的形状切换为椭圆
        scenes = new LinkedList<>();
        DoorWidth = 0.9;  //FIXME 门宽
        density = 20;
        setUpT1Scene5();
        for (Scene scene : scenes) {
            scene.setApplication(this);
        }
    }

    public void Reader() {
        try {
            String[] container = new String[30];
            File f = new File(System.getProperty("user.dir") + "/resource/output/Ellipse.csv");
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                String item[] = line.split("\"");
                String container_of_ped[] = new String[item.length / 2];
                for (int i = 0; i < item.length; i++) {
                    if (i % 2 == 0) {
                        continue;
                    }
                    container_of_ped[i / 2] = item[i];
                }
                for (int i = 0; i < container_of_ped.length; i++) {
                    container_of_ped[i] = container_of_ped[i].substring(1, container_of_ped[i].length() - 1);
                    String subSrring[] = container_of_ped[i].split(",");
                    container_of_ped[i] = subSrring[1] + "," + subSrring[0];
                }
                String container_of_everyone[] = new String[30];
                for (int i = 0; i < container_of_everyone.length; i++) {
                    container_of_everyone[i] = "";
                }
                for (int i = 0; i < container_of_ped.length; i++) {
                    int m = i % 30;
//                    container_of_ped[i] = container_of_ped[i].substring(1, container_of_ped[i].length() - 1);
                    container_of_everyone[m] += "\"" + container_of_ped[i] + "\",";
                }
                for (int i = 0; i < container_of_everyone.length; i++) {
                    container_of_everyone[i] = container_of_everyone[i].substring(0, container_of_everyone[i].length() - 1);
                }
                try {
                    String FileName = "transEllipse.csv";
                    String FilePath = System.getProperty("user.dir") + "/resource/output/";
                    File csv = null;
                    csv = new File(FilePath + FileName);
                    csv.createNewFile();
                    File ff = new File(FilePath + FileName);
                    FileWriter fffw = new FileWriter(ff);
                    BufferedWriter ffbw = new BufferedWriter(fffw);
                    for (int i = 0; i < container_of_everyone.length; i++) {
                        ffbw.write(container_of_everyone[i]);
                        ffbw.newLine();
                    }
                    ffbw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 门前有柱子
     */
    protected void setUpT1Scene5() {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("T1.s");
        Interpreter interpreter = new SimpleInterpreter();
        interpreter.loadFrom(is);
        SceneLoader loader = interpreter.setLoader().setModel(new SimpleForceModel());
        SimpleParameterPool parameters = new SimpleParameterPool();
        /*parameters.addValuesAsParameter(
                new SimpleEntityGenerator()
                        .setValue(new Wall(new Box2D(4,-4,2,2)))
                        .setPriority(10)
                ,new SimpleEntityGenerator()
                        .setValue(new Wall(new Circle2D(new Point2D(5,-3),1)))
                        .setPriority(10)
        );*/

        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new SafetyRegion(new Box2D(1, 10, 8, 1)))
                .addValue(new Monitor(new Box2D(0, 0, 10, 1)))

        );

        parameters.addValuesAsParameter(new SimpleEntityGenerator()
                .setValue(new DoorTurn(new Box2D(5 - DoorWidth / 2, -0.01, DoorWidth, 1.02), new Segment2D(new Point2D(5 - DoorWidth / 2, -0.1), new Point2D(5 + DoorWidth / 2, -0.1)), 0.4))  //FIXME 主动侧身作用区域（出口）的大小
                .setPriority(5)
        );

//        parameters.addValuesAsParameter(
//                new RandomEntityGenerator2D(30,new Box2D(0,-10,10,5))
//                        .setValue(template)
//                        .setGaussianParameter(1,0.025)
//                FIXME 行人的总数（修改“26”）
//        );
        parameters.addValuesAsParameter(new MultipleEntitiesGenerator()
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.63279 + 10.23779, 7.33454 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.95263 + 10.23779, 7.18216 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.41848 + 10.23779, 7.12121 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.68114 + 10.23779, 6.86724 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.92845 + 10.23779, 6.74534 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.33387 + 10.23779, 6.22725 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.62071 + 10.23779, 6.34915 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-4.79875 + 10.23779, 6.30852-7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-4.2669 + 10.23779, 6.22725 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.63835 + 10.23779, 6.43042 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.92616 + 10.23779, 6.14598 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.98333 + 10.23779, 5.84122 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.41522 + 10.23779, 5.89201 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.6867 + 10.23779, 5.73964 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.56354 + 10.23779, 5.61773 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.3097 + 10.23779, 5.27234 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.93498 + 10.23779, 5.4044 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.34269 + 10.23779, 5.26218 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-4.39987 + 10.23779, 5.31297 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.37242 + 10.23779, 5.24186 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-6.80529 + 10.23779, 5.03869 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.94707 + 10.23779, 4.85583 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.02842 + 10.23779, 4.66282 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-4.48448 + 10.23779, 4.51044 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.9768 + 10.23779, 4.47996 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-5.14929 + 10.23779, 4.04314 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-4.56909 + 10.23779, 3.77902 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-3.55374 + 10.23779, 3.95171 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-7.94152 + 10.23779, 7.36502 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
                .addValue(new BaseAgent(new Ellipse2D(0.45 / 2, 0.25 / 2, new Point2D(-2.06697 + 10.23779, 5.18091 -7.81154-5), Math.PI), new Velocity2D(0, 1)))
//
                .setCommonName("Agent"));

        /*parameters.addValuesAsParameter(
                new RandomEntityGenerator2D(1,new Box2D(4.3,-3,0.5,2)).setValue(template)
        );*/

        loader.readParameterSet(parameters);
        for (Scene s : loader.readScene()) {
            scenes.add(s);
        }

    }
}
