package org.socialforce.drawer.impl;

import org.socialforce.drawer.Drawable;
import org.socialforce.drawer.Drawer;
import org.socialforce.geom.*;
import org.socialforce.geom.Point;
import org.socialforce.geom.impl.Point2D;
import org.socialforce.model.Agent;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Ledenel on 2017/3/3.
 */
public class GoalDynamicColorMarkDrawer extends DynamicColorDrawer<Agent> {
    private Color baseColor = Color.yellow;
    private LinkedList<Support> supports = new LinkedList<>();
    public class Support{
        Point goal;
        Color color;

        public Support(Point goal, Color color) {
            this.goal = goal;
            this.color = color;
        }
    }

    public GoalDynamicColorMarkDrawer(Graphics2D device) {
        super(device);
        supports.add(new Support(new Point2D(26.0,2.5), Color.red));
        supports.add(new Support(new Point2D(33.5,14), Color.green));
        supports.add(new Support(new Point2D(14.0,21.5), Color.CYAN));
        supports.add(new Support(new Point2D(-5.5,5), Color.green));
    }

    @Override
    public Color currentColor(Agent agent) {
        Point goal = agent.getPath().getGoal();
        for(Support support:supports){
            if(goal.equals(support.goal)) return support.color;
        }

        return baseColor;
        //         if(entity instanceof Agent && ((Agent) entity).getPath().getGoal().equals(new Point2D(33.5,14))) ((AwtDrawer2D)drawer).setColor(Color.green);
        //         if(entity instanceof Agent && ((Agent) entity).getPath().getGoal().equals(new Point2D(14.0,21.5))) ((AwtDrawer2D)drawer).setColor(Color.blue);
    }

    public void setBaseColor(Color color){
        this.baseColor = color;
    }

    public void addSupport(Point goal, Color color){
        supports.add(new Support(goal,color));
    }


}
