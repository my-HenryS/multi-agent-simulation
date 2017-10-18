package org.socialforce.model.impl;


import org.socialforce.geom.PhysicalEntity;
import org.socialforce.geom.impl.*;
import org.socialforce.model.Agent;
import org.socialforce.model.Influential;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15 0015.
 */
public class DoorTurn extends Exit implements Influential {

    protected Box2D door;
    protected Segment2D doorLine;     //过门线（门内侧）
    protected double width;


    public DoorTurn(Box2D box2D, Segment2D segment2D, double width) {
        super(box2D);
        door = box2D;
        doorLine = segment2D;
        this.width = width;
    }

    /**
     * 门前预判区域
     * @param
     * @return
     */
    public Rectangle2D getDoorZone(){
        doorLine.clone().moveTo(doorLine.getReferencePoint().clone().moveBy(0,width/(-2)));
        return doorLine.flatten(width);
    }

    @Override
    public DoorTurn clone() {
        return new DoorTurn((Box2D) door.clone(),doorLine.clone(),width);
    }


    @Override
    public PhysicalEntity getView() {
        return new DoubleShape2D(door.clone(), getDoorZone().clone());
    }

    @Override
    public void affect(Agent target) {
        target.push(model.interactAffection(this,target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {
        List<Agent> judgedAgent = new ArrayList<>();
        List<Segment2D> blockedLine = new ArrayList<>();
        for (Agent agent : affectableAgents) {
            if(agent.getPhysicalEntity().intersects(doorLine)) {
                judgedAgent.add(agent);
                affect(agent);
                blockedLine.add(((Ellipse2D) agent.getPhysicalEntity()).getProjection(doorLine));
            }
            else if(agent.getPhysicalEntity().intersects(door))
                affect(agent);
        }
        if(!judgedAgent.isEmpty()) {
            Segment2D[] restLine = doorLine.remove(blockedLine);
            Segment2D agentProjection;
            for (Agent agent : affectableAgents) {
                if (agent.getPhysicalEntity().intersects(getDoorZone())) {
                    agentProjection = ((Ellipse2D) agent.getPhysicalEntity()).getProjection(doorLine);
                    Segment2D hitProjection = new Segment2D(new Point2D(0, 0), new Point2D(1.0e-7, 0));
                    for (Segment2D segment : restLine) {
                        if ((agentProjection.intersect(segment)) && (hitProjection.getLenth() < segment.getLenth()))
                            hitProjection = segment;
                    }
                    if (agentProjection.getLenth() > hitProjection.getLenth()) {
                        ((BaseAgent) agent).setExpectedAngle(Math.acos(hitProjection.getLenth() / (2 * ((Ellipse2D) agent.getPhysicalEntity()).getA())));
                        affect(agent);
                    }
                }
            }
        }
    }



}