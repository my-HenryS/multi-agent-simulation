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


    public DoorTurn(Box2D box2D, Segment2D segment2D) {
        super(box2D);
        door = box2D;
        doorLine = segment2D;
    }

    /**
     * 门前预判区域
     * @param width
     * @return
     */
    public Rectangle2D getDoorZone(double width){
        doorLine.clone().moveTo(doorLine.getReferencePoint().clone().moveBy(0,width/(-2)));
        return doorLine.flatten(width);
    }

    @Override
    public DoorTurn clone() {
        return new DoorTurn((Box2D) door.clone(),doorLine.clone());
    }


    @Override
    public PhysicalEntity getView() {
        return new DoubleShape2D(door.clone(), getDoorZone(0.4).clone());
    }

    @Override
    public void affect(Agent target) {
        target.push(model.interactAffection(this,target));
    }

    @Override
    public void affectAll(Iterable<Agent> affectableAgents) {

        List<Agent> judgedAgent = new ArrayList<>();
        //List<Agent> unjudgedAgent = new ArrayList<>();
        List<Segment2D> blockedLine = new ArrayList<>();
        for (Agent agent : affectableAgents) {
            if(agent.getPhysicalEntity().intersects(doorLine)) {
                judgedAgent.add(agent);
                affect(agent);
                blockedLine.add(((Ellipse2D) agent.getPhysicalEntity()).getProjection(doorLine));
            }
        }


        if (!judgedAgent.isEmpty()) {
            for (Agent agent : judgedAgent){
                affect(agent);
                blockedLine.add(((Ellipse2D) agent.getPhysicalEntity()).getProjection(doorLine));
            }
            double expectedAngle;
            Segment2D agentProjection;
            Segment2D comparedProjection = new Segment2D(new Point2D(0, 0), new Point2D(1.0e-7, 0));  //???
            Segment2D[] restLine = doorLine.remove(blockedLine);
            for (Agent agent : unjudgedAgent) {
                ((BaseAgent) agent).DoorTurnUnjudged = false;
                agentProjection = ((Ellipse2D) agent.getPhysicalEntity()).getProjection(doorLine);
                for(Segment2D segment : restLine) {
                    if ((agentProjection.intersect(segment)) && (comparedProjection.getLenth() < segment.getLenth()))
                        comparedProjection = segment;
                }
                if (agentProjection.getLenth() > comparedProjection.getLenth()) {
                    expectedAngle = Math.acos(comparedProjection.getLenth() / (2 * ((Ellipse2D) agent.getPhysicalEntity()).getA()));
                    ((BaseAgent) agent).setExpectedAngle(expectedAngle);
                    affect(agent);
                }
            }
        }
    }
}