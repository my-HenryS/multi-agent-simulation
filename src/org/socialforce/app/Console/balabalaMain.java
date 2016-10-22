package org.socialforce.app.Console;

import org.socialforce.app.ApplicationListener;
import org.socialforce.app.Scene;
import org.socialforce.app.SimpleApplication;
import org.socialforce.app.SocialForceApplication;
import org.socialforce.geom.impl.Circle2D;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;

/**
 * Created by Whatever on 2016/10/22.
 */
public class balabalaMain implements ApplicationListener {
    public static void main(String[] args) {
        balabalaMain balabalaMain = new balabalaMain();
        balabalaMain.setApplication(new SimpleApplication());
        balabalaMain.getApplication().setApplicationListener(balabalaMain);
        balabalaMain.getApplication().start();
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
        Circle2D circle = new Circle2D(new Point2D(0,0),100);
        while (!scene.getAllAgents().isEmpty()){
            int time = scene.getCurrentSteps();
            if(time%10 == 0){
            System.out.println("目前时间"+time);
            System.out.println("场景中人数"+scene.getAllAgents().size());
            System.out.println("目前一号智能体的位置是"+scene.getAllAgents().selectTop(circle).getShape().getReferencePoint().getX()+
                    ","+scene.getAllAgents().selectTop(circle).getShape().getReferencePoint().getY());
            }
        }
    }
}
