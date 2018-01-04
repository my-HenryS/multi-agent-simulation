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
    }

    @Override
    public Color currentColor(Agent agent) {
        if(agent.getPath() != null) {
            Point goal = agent.getPath().getGoal(); // TODO 2018/1/4 agent may not have paths.
            for (Support support : supports) {
                if (goal.equals(support.goal)) return support.color;
            }
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
