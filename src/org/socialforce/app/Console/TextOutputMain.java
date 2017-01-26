package org.socialforce.app.Console;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Applications.ApplicationForCanteen;
import org.socialforce.app.Applications.ApplicationForECStrategy;
import org.socialforce.scene.Scene;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;

/**
 * Created by Whatever on 2016/10/22.
 */
public class TextOutputMain implements ApplicationListener {
    public static void main(String[] args) {
        TextOutputMain TextOutputMain = new TextOutputMain();
        TextOutputMain.setApplication(new ApplicationForECStrategy());//改应用在这里！
        TextOutputMain.getApplication().setApplicationListener(TextOutputMain);
        TextOutputMain.getApplication().start();
    }

    public SocialForceApplication getApplication() {
        return application;
    }

    public void setApplication(SocialForceApplication application) {
        this.application = application;
    }

    SocialForceApplication application;

    /**
     * triggered while a agent is escaped.
     *
     * @param scene       the scene where the agent in.
     * @param escapeAgent the escaped agent.
     */
    @Override
    public void onAgentEscape(Scene scene, Agent escapeAgent) {

    }

    /**
     * triggered while the application is start.
     */
    @Override
    public void onStart() {

    }

    /**
     * triggered while the application is stop.
     *
     * @param terminated whether the application is forced to shut down.
     */
    @Override
    public void onStop(boolean terminated) {

    }

    /**
     * triggered while a scene is step-forwarded.
     *
     * @param scene the scene steped-forwarded.
     */
    @Override
    public void onStep(Scene scene) {
        //TODO: Add your output code HERE
        Circle2D circle = new Circle2D(new Point2D(0, 0), 100);
        String[][] SquareRoom = new String[100][80];
        int number = scene.getAllAgents().size();
        int time = scene.getCurrentSteps();
        if (scene.getAllAgents().size() == 5) {
            /*if(time%10 ==0){
            System.out.println("目前时间" + time);
            System.out.println("场景中人数" + scene.getAllAgents().size());
            System.out.println("目前一号智能体的位置是" + scene.getAllAgents().selectTop(circle).getShape().getReferencePoint().getX() +
                    "," + scene.getAllAgents().selectTop(circle).getShape().getReferencePoint().getY());*/
            //if (time % 50 == 0) {
               // System.out.println();System.out.println();
                System.out.println("result is : " + time*0.002);
            //System.out.println();System.out.println();Iterable<Agent> agents = scene.getAllAgents();
                /*
                for (int i = -20; i < 80; i++) {
                    for (int j = -20; j < 60; j++) {
                        //Point2D here = new Point2D(i, j);
                        Circle2D here = new Circle2D(new Point2D(0.5*i,0.5*j),0.05);
                        int count = 0;
                        Iterable<Agent> agents = scene.getAllAgents().select(here);
                        for (InteractiveEntity agent : agents) {
                            count++;
                        }
                        if (count >= 1) {
                            SquareRoom[i + 20][j + 20] = "@";
                            continue;
                        }
                        count = 0;
                        Iterable<InteractiveEntity> entities = scene.getStaticEntities().select(here);
                        for (InteractiveEntity entity : entities) {
                            count++;
                        }
                        if (count >= 1) {
                            SquareRoom[i + 20][j + 20] = "O";
                            continue;
                        }
                        SquareRoom[i + 20][j + 20] = "+";
                    }
                }

                for (int i = 0; i < 80; i++) {
                    for (int j = 0; j < 100; j++) {
                        System.out.print(SquareRoom[j][i]);
                    }
                    System.out.println();
                }
            */
            //}
        }
    }

}
